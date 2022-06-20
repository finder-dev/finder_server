package com.cmc.finder.api.qna.qustion.service;

import com.cmc.finder.api.qna.qustion.dto.*;
import com.cmc.finder.api.qna.qustion.repository.QuestionRepositoryCustom;
import com.cmc.finder.domain.answer.entity.Answer;
import com.cmc.finder.domain.answer.service.AnswerService;
import com.cmc.finder.domain.model.Email;
import com.cmc.finder.domain.model.MBTI;
import com.cmc.finder.domain.question.entity.Curious;
import com.cmc.finder.domain.question.entity.Question;
import com.cmc.finder.domain.question.entity.QuestionFavorite;
import com.cmc.finder.domain.question.entity.QuestionImage;
import com.cmc.finder.domain.question.service.CuriousService;
import com.cmc.finder.domain.question.service.QuestionFavoriteService;
import com.cmc.finder.domain.question.service.QuestionService;
import com.cmc.finder.domain.user.entity.User;
import com.cmc.finder.domain.user.service.UserService;
import com.cmc.finder.domain.viewcount.entity.ViewCount;
import com.cmc.finder.domain.viewcount.service.ViewCountService;
import com.cmc.finder.infra.file.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ApiQuestionService {

    @Value("${s3.question.path}")
    private String PATH;

    private final UserService userService;
    private final QuestionService questionService;
    private final QuestionRepositoryCustom questionRepositoryCustom;
    private final ViewCountService viewCountService;
    private final CuriousService curiousService;
    private final QuestionFavoriteService questionFavoriteService;

    private final AnswerService answerService;
    private final S3Uploader s3Uploader;

    @Transactional
    public QuestionCreateDto.Response createQuestion(QuestionCreateDto.Request request, String email) {

        // 유저 조회
        User user = userService.getUserByEmail(Email.of(email));

        // 질문 생성
        Question question = Question.createQuestion(request.getTitle(), request.getContent(), MBTI.valueOf(request.getMbti()), user);

        // 질문 이미지 생성 및 저장
//        List<QuestionImage> questionImages = new ArrayList<>();
        for (int i = 0; i < request.getQuestionImgs().size(); i++) {
            MultipartFile multipartFile = request.getQuestionImgs().get(i);
            String imageName = s3Uploader.uploadFile(multipartFile, PATH);
            String url = s3Uploader.getUrl(PATH, imageName);
            QuestionImage questionImage;
            if (i != 0) {
                questionImage = QuestionImage.createQuestionImage(question, imageName, url, false);
            } else {
                questionImage = QuestionImage.createQuestionImage(question, imageName, url, true);
            }
            question.addQuestionImage(questionImage);
//            questionImages.add(questionImage);
        }

        // 질문 저장
        questionService.create(question);

//        List<QuestionImage> questionImages = request.getQuestionImgs().stream().map(multipartFile -> {
//            String imageName = s3Uploader.uploadFile(multipartFile, PATH);
//            String url = s3Uploader.getUrl(PATH, imageName);
//            return QuestionImage.createQuestionImage(question, imageName, url);
//        }).collect(Collectors.toList());

//        questionImageService.save(questionImages);

        return QuestionCreateDto.Response.of(question);

    }

    public Page<QuestionSimpleDto.Response> getQuestionList(Pageable pageable, MBTI mbti) {

        Page<QuestionSimpleDto.Response> questionPage = questionRepositoryCustom.findQuestionSimpleDto(pageable, mbti);
        return questionPage;

    }

    public QuestionDetailDto getQuestionDetail(Long questionId, String email) {

        // 유저 조회 -> 조회수 증가
        User user = userService.getUserByEmail(Email.of(email));

        // 질문 조회
        Question question = questionService.getQuestionFetchQuestionImage(questionId);

        // 조회수 증가
        if (!viewCountService.alreadyReadUser(question, user)) {
            ViewCount viewCount = ViewCount.createViewCount(question, user);
            question.addViewCount(viewCount);
            viewCountService.addViewCount(viewCount);
        }

        // 조회수
        Long viewCount = viewCountService.getViewCount(question);

        // 답변 조회
        List<Answer> answers = answerService.getAnswersByQuestionId(question.getQuestionId());

        // 이미 즐겨찾기?
        Boolean favoriteUser = questionFavoriteService.existsUser(question, user);

        // 이미 궁금해요 누른경우?
        Boolean alreadyCuriousUser = curiousService.existsUser(question, user);

        QuestionDetailDto questionDetailDto = QuestionDetailDto.of(question, answers, viewCount, favoriteUser, alreadyCuriousUser);
        return questionDetailDto;

    }

    @Transactional
    public CuriousAddOrDeleteDto addOrDeleteCurious(Long questionId, String email) {

        Question question = questionService.getQuestion(questionId);
        User user = userService.getUserByEmail(Email.of(email));

        if (curiousService.existsUser(question, user)) {
            curiousService.delete(question, user);
            return CuriousAddOrDeleteDto.of(false);
        }

        Curious curious = Curious.createCurious(question, user);
        question.addCurious(curious);

        curiousService.create(curious);

        return CuriousAddOrDeleteDto.of(true);

    }


    @Transactional
    public QuestionFavoriteAddOrDeleteDto addOrDeleteFavorite(Long questionId, String email) {

        Question question = questionService.getQuestion(questionId);
        User user = userService.getUserByEmail(Email.of(email));

        if (questionFavoriteService.existsUser(question, user)) {
            questionFavoriteService.delete(question, user);
            return QuestionFavoriteAddOrDeleteDto.of(false);
        }

        QuestionFavorite questionFavorite = QuestionFavorite.createQuestionFavorite(question, user);
        questionFavoriteService.create(questionFavorite);

        return QuestionFavoriteAddOrDeleteDto.of(true);

    }

    public void deleteFavorite(Long questionId, String email) {

        //TODO 유저 검증
        Question question = questionService.getQuestion(questionId);
        User user = userService.getUserByEmail(Email.of(email));

        questionFavoriteService.delete(question, user);
    }

    public void updateQuestion(Long questionId, QuestionUpdateDto.Request request, String email) {

        // 유저 검증
        User user = userService.getUserByEmail(Email.of(email));

        // TODO 검증 추가

        // 질문 정보 변경
        Question question = updateQuestionInfo(questionId, request);

        // 질문 이미지 변경
        updateQuestionImages(question, request);


    }

    private void updateQuestionImages(Question question, QuestionUpdateDto.Request request) {
        // 질문 이미지
        List<QuestionImage> questionImages = question.getQuestionImages();

        for (QuestionImage questionImage : questionImages) {


        }

        if (request.getQuestionUpdateImgs() != null) {


        }


    }

    private Question updateQuestionInfo(Long questionId, QuestionUpdateDto.Request request) {

        Question updatequestion = request.toEntity();
        Question updatedQuestion = questionService.updateQuestion(questionId, updatequestion);

        return updatedQuestion;

    }


}
