package com.cmc.finder.api.qna.answer.service;

import com.cmc.finder.api.qna.answer.dto.AnswerCreateDto;
import com.cmc.finder.api.qna.answer.dto.AnswerDeleteDto;
import com.cmc.finder.api.qna.answer.dto.HelpfulAddOrDeleteDto;
import com.cmc.finder.domain.qna.answer.entity.Answer;
import com.cmc.finder.domain.qna.answer.entity.AnswerImage;
import com.cmc.finder.domain.qna.answer.entity.Helpful;
import com.cmc.finder.domain.qna.answer.service.AnswerService;
import com.cmc.finder.domain.qna.answer.service.HelpfulService;
import com.cmc.finder.domain.model.Email;
import com.cmc.finder.domain.qna.question.entity.Question;
import com.cmc.finder.domain.qna.question.service.QuestionService;
import com.cmc.finder.domain.user.entity.User;
import com.cmc.finder.domain.user.service.UserService;
import com.cmc.finder.global.error.exception.AuthenticationException;
import com.cmc.finder.global.error.exception.BusinessException;
import com.cmc.finder.global.error.exception.ErrorCode;
import com.cmc.finder.infra.file.S3Uploader;
import com.cmc.finder.infra.notification.FCMService;
import com.cmc.finder.infra.notification.exception.NotificationFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.cmc.finder.global.util.Constants.QUESTION_ANSWER;


@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ApiAnswerService {

    @Value("${s3.answer.path}")
    private String PATH;

    private final UserService userService;
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final HelpfulService helpfulService;
    private final FCMService fcmService;

    private final S3Uploader s3Uploader;

    @Transactional
    public AnswerCreateDto.Response createAnswer(Long questionId, AnswerCreateDto.Request request, String email) {

        // 유저 조회
        User user = userService.getUserByEmail(Email.of(email));

        // 질문 조회
        Question question = questionService.getQuestionFetchUser(questionId);

        // 답변 생성
        Answer answer = request.toEntity();
        Answer saveAnswer = Answer.createAnswer(answer, user, question);
        question.addAnswer(saveAnswer);

        // 답변 이미지 생성
        request.getAnswerImgs().stream().forEach(multipartFile -> {
            String imageName = s3Uploader.uploadFile(multipartFile, PATH);
            String url = s3Uploader.getUrl(PATH, imageName);

            AnswerImage answerImage = AnswerImage.createAnswerImage(answer, imageName, url);
            saveAnswer.addAnswerImage(answerImage);
        });

        Answer savedAnswer = answerService.create(saveAnswer);

        fcmService.sendMessageTo(question.getUser().getFcmToken(), question.getTitle(), QUESTION_ANSWER);

        return AnswerCreateDto.Response.of(savedAnswer);

    }

    @Transactional
    public HelpfulAddOrDeleteDto addOrDeleteHelpful(Long answerId, String email) {

        Answer answer = answerService.getAnswer(answerId);
        User user = userService.getUserByEmail(Email.of(email));

        if (helpfulService.existsUser(answer, user)) {
            helpfulService.delete(answer, user);
            return HelpfulAddOrDeleteDto.of(false);
        }


        Helpful helpful = Helpful.createHelpful(answer, user);
        answer.addHelpful(helpful);

        helpfulService.create(helpful);

        return HelpfulAddOrDeleteDto.of(true);

    }

    @Transactional
    public AnswerDeleteDto deleteAnswer(Long answerId, String email) {

        User user = userService.getUserByEmail(Email.of(email));
        Answer answer = answerService.getAnswer(answerId);

        if (answer.getUser() != user) {
            throw new AuthenticationException(ErrorCode.ANSWER_USER_BE_NOT_WRITER);
        }

        answerService.deleteAnswer(answer);

        return AnswerDeleteDto.of();
    }
}
