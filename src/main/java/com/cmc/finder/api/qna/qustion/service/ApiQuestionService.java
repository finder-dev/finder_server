package com.cmc.finder.api.qna.qustion.service;

import com.cmc.finder.api.qna.qustion.dto.*;
import com.cmc.finder.api.qna.qustion.repository.QuestionRepositoryCustom;
import com.cmc.finder.domain.qna.answer.entity.Answer;
import com.cmc.finder.domain.qna.answer.service.AnswerService;
import com.cmc.finder.domain.model.Email;
import com.cmc.finder.domain.model.MBTI;
import com.cmc.finder.domain.qna.question.entity.Curious;
import com.cmc.finder.domain.qna.question.entity.Question;
import com.cmc.finder.domain.qna.question.entity.QuestionFavorite;
import com.cmc.finder.domain.qna.question.entity.QuestionImage;
import com.cmc.finder.domain.qna.question.exception.QuestionImageMaxException;
import com.cmc.finder.domain.qna.question.service.CuriousService;
import com.cmc.finder.domain.qna.question.service.QuestionFavoriteService;
import com.cmc.finder.domain.qna.question.service.QuestionImageService;
import com.cmc.finder.domain.qna.question.service.QuestionService;
import com.cmc.finder.domain.user.entity.User;
import com.cmc.finder.domain.user.service.UserService;
import com.cmc.finder.domain.viewcount.entity.ViewCount;
import com.cmc.finder.domain.viewcount.service.ViewCountService;
import com.cmc.finder.global.error.exception.AuthenticationException;
import com.cmc.finder.global.error.exception.ErrorCode;
import com.cmc.finder.infra.file.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ApiQuestionService {

    @Value("${s3.question.path}")
    private String PATH;

    private final UserService userService;
    private final QuestionService questionService;
    private final QuestionImageService questionImageService;
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
        request.getQuestionImgs().stream().forEach(multipartFile -> {
            String imageName = s3Uploader.uploadFile(multipartFile, PATH);
            String url = s3Uploader.getUrl(PATH, imageName);

            QuestionImage questionImage = QuestionImage.createQuestionImage(question, imageName, url);
            question.addQuestionImage(questionImage);

        });

//        for (int i = 0; i < request.getQuestionImgs().size(); i++) {
//            MultipartFile multipartFile = request.getQuestionImgs().get(i);
//            String imageName = s3Uploader.uploadFile(multipartFile, PATH);
//            String url = s3Uploader.getUrl(PATH, imageName);
//            QuestionImage questionImage;
//            if (i != 0) {
//                questionImage = QuestionImage.createQuestionImage(question, imageName, url, false);
//            } else {
//                questionImage = QuestionImage.createQuestionImage(question, imageName, url, true);
//            }
//            question.addQuestionImage(questionImage);
////            questionImages.add(questionImage);
//        }

        // 질문 저장
        questionService.create(question);



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


    @Transactional
    public QuestionUpdateDto.Response updateQuestion(Long questionId, QuestionUpdateDto.Request request, String email) {

        User user = userService.getUserByEmail(Email.of(email));
        Question question = questionService.getQuestion(questionId);

        // 유저 검증
        if (question.getUser() != user) {
           throw new AuthenticationException(ErrorCode.QUESTION_USER_BE_NOT_WRITER);
        }

        // 질문 정보 변경
        Question updatedQuestion = updateQuestionInfo(question, request);
        updateQuestionImages(updatedQuestion, request);

        // 이미지 10개 초과 검증
        if (updatedQuestion.getQuestionImages().size() > 10) {
            throw new QuestionImageMaxException(ErrorCode.QUESTION_IMAGE_MAX);
        }

        return QuestionUpdateDto.Response.of(updatedQuestion);

    }

    private void updateQuestionImages(Question question, QuestionUpdateDto.Request request) {

        // 질문 이미지 삭제
        request.getDeleteImgIds().stream().forEach(deleteImgId -> {
            QuestionImage questionImage = questionImageService.getQuestionImage(question.getQuestionId(), deleteImgId);
            s3Uploader.deleteFile(questionImage.getImageName(), PATH);
            question.deleteQuestionImage(questionImage);
//            questionImageService.delete(questionImage);

        });

        // 질문 이미지 추가
        request.getAddImgs().stream().forEach(multipartFile -> {

            String imageName = s3Uploader.uploadFile(multipartFile, PATH);
            String url = s3Uploader.getUrl(PATH, imageName);

            QuestionImage questionImage = QuestionImage.createQuestionImage(question, imageName, url);
            question.addQuestionImage(questionImage);

        });

    }

    private Question updateQuestionInfo(Question question, QuestionUpdateDto.Request request) {

        Question updateQuestion = request.toEntity();
        Question updatedQuestion = questionService.updateQuestion(question, updateQuestion);

        return updatedQuestion;

    }

    @Transactional
    public QuestionDeleteDto deleteQuestion(Long questionId, String email) {

        User user = userService.getUserByEmail(Email.of(email));
        Question question = questionService.getQuestion(questionId);

        // 유저 검증
        if (question.getUser() != user) {
            throw new AuthenticationException(ErrorCode.QUESTION_USER_BE_NOT_WRITER);
        }

        questionService.deleteQuestion(question);

        return QuestionDeleteDto.of();
    }
}
