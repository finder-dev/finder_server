package com.cmc.finder.api.qna.qustion.application;

import com.cmc.finder.api.qna.qustion.dto.*;
import com.cmc.finder.domain.qna.question.repository.QuestionRepositoryCustom;
import com.cmc.finder.domain.qna.answer.entity.Answer;
import com.cmc.finder.domain.qna.answer.service.AnswerService;
import com.cmc.finder.domain.model.Email;
import com.cmc.finder.domain.model.MBTI;
import com.cmc.finder.domain.qna.question.entity.*;
import com.cmc.finder.domain.qna.question.exception.QuestionImageMaxException;
import com.cmc.finder.domain.qna.question.service.CuriousService;
import com.cmc.finder.domain.qna.question.service.FavoriteQuestionService;
import com.cmc.finder.domain.qna.question.service.QuestionImageService;
import com.cmc.finder.domain.qna.question.service.QuestionService;
import com.cmc.finder.domain.user.entity.User;
import com.cmc.finder.domain.user.service.UserService;
import com.cmc.finder.domain.qna.question.service.ViewCountService;
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
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ApiQuestionService {

    @Value("${s3.question.path}")
    private String PATH;

    private final UserService userService;
    private final QuestionService questionService;
    private final QuestionImageService questionImageService;
    private final ViewCountService viewCountService;
    private final CuriousService curiousService;
    private final FavoriteQuestionService favoriteQuestionService;

    private final AnswerService answerService;
    private final S3Uploader s3Uploader;

    @Transactional
    public QuestionCreateDto.Response createQuestion(QuestionCreateDto.Request request, String email) {

        // 유저 조회
        User user = userService.getUserByEmail(Email.of(email));

        // 질문 생성
        Question question = request.toEntity();
        Question saveQuestion = Question.createQuestion(question, user);

        // 질문 이미지 생성 및 저장
        request.getQuestionImgs().stream().forEach(multipartFile -> {
            String imageName = s3Uploader.uploadFile(multipartFile, PATH);
            String url = s3Uploader.getUrl(PATH, imageName);

            QuestionImage questionImage = QuestionImage.createQuestionImage(question, imageName, url);
            saveQuestion.addQuestionImage(questionImage);

        });

        // 질문 저장
        Question savedQuestion = questionService.create(saveQuestion);

        return QuestionCreateDto.Response.of(savedQuestion);

    }

    public Page<QuestionSimpleDto.Response> getQuestionList(Pageable pageable, MBTI mbti) {

        Page<QuestionSimpleDto.Response> questionPage = questionService.getQuestionList(pageable, mbti);
        return questionPage;

    }

    public QuestionDetailDto getQuestionDetail(Long questionId, String email) {

        // 유저 조회 -> 조회수 증가
        User user = userService.getUserByEmail(Email.of(email));

        // 질문 조회
        Question question = questionService.getQuestionFetchUser(questionId);

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
        Boolean favoriteUser = favoriteQuestionService.existsUser(question, user);

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
    public FavoriteQuestionAddOrDeleteDto addOrDeleteFavorite(Long questionId, String email) {

        Question question = questionService.getQuestion(questionId);
        User user = userService.getUserByEmail(Email.of(email));

        if (favoriteQuestionService.existsUser(question, user)) {
            favoriteQuestionService.delete(question, user);
            return FavoriteQuestionAddOrDeleteDto.of(false);
        }

        FavoriteQuestion favoriteQuestion = FavoriteQuestion.createFavoriteQuestion(question, user);
        favoriteQuestionService.create(favoriteQuestion);

        return FavoriteQuestionAddOrDeleteDto.of(true);

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
        Question updatedQuestion = updateQuestionInfo(questionId, request);
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

        });

        // 질문 이미지 추가
        request.getAddImgs().stream().forEach(multipartFile -> {

            String imageName = s3Uploader.uploadFile(multipartFile, PATH);
            String url = s3Uploader.getUrl(PATH, imageName);

            QuestionImage questionImage = QuestionImage.createQuestionImage(question, imageName, url);
            QuestionImage savedQuestionImage = questionImageService.save(questionImage);
            question.addQuestionImage(savedQuestionImage);

        });

    }

    private Question updateQuestionInfo(Long questionId, QuestionUpdateDto.Request request) {

        Question updateQuestion = request.toEntity();
        Question updatedQuestion = questionService.updateQuestion(questionId, updateQuestion);

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

    public List<FavoriteQuestionRes> getFavoriteQuestion(String email) {

        User user = userService.getUserByEmail(Email.of(email));

        List<FavoriteQuestion> favoriteQuestionFetchQuestion = favoriteQuestionService.getFavoriteQuestionFetchQuestion(user);

        return favoriteQuestionFetchQuestion.stream().map(
                favoriteQuestion -> FavoriteQuestionRes.of(user, favoriteQuestion)
        ).collect(Collectors.toList());

    }

    public List<QuestionHotDto> getHotQuestion() {
        List<Question> hotQuestions = questionService.getHotQuestion();
        return hotQuestions.stream().map(question ->
                QuestionHotDto.of(question)).collect(Collectors.toList());

    }
}
