package com.cmc.finder.api.qna.answer.application;

import com.cmc.finder.api.qna.answer.dto.*;
import com.cmc.finder.domain.model.ServiceType;
import com.cmc.finder.domain.notification.entity.Notification;
import com.cmc.finder.domain.notification.application.NotificationService;
import com.cmc.finder.domain.qna.answer.entity.Answer;
import com.cmc.finder.domain.qna.answer.entity.AnswerImage;
import com.cmc.finder.domain.qna.answer.entity.AnswerReply;
import com.cmc.finder.domain.qna.answer.entity.Helpful;
import com.cmc.finder.domain.qna.answer.service.AnswerService;
import com.cmc.finder.domain.qna.answer.service.HelpfulService;
import com.cmc.finder.domain.model.Email;
import com.cmc.finder.domain.qna.answer.service.AnswerReplyService;
import com.cmc.finder.domain.qna.question.entity.Question;
import com.cmc.finder.domain.qna.question.application.QuestionService;
import com.cmc.finder.domain.user.entity.User;
import com.cmc.finder.domain.user.application.UserService;
import com.cmc.finder.global.error.exception.AuthenticationException;
import com.cmc.finder.global.error.exception.ErrorCode;
import com.cmc.finder.infra.file.S3Uploader;
import com.cmc.finder.infra.notification.FcmService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.cmc.finder.global.util.Constants.QUESTION_ANSWER;
import static com.cmc.finder.global.util.Constants.QUESTION_ANSWER_REPLY;


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
    private final FcmService fcmService;
    private final AnswerReplyService answerReplyService;
    private final NotificationService notificationService;

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

        // 알림 생성
        fcmService.sendMessageTo(question.getUser().getFcmToken(), question.getTitle(), QUESTION_ANSWER, ServiceType.QUESTION.getValue());
        createNotification(question, QUESTION_ANSWER);

        return AnswerCreateDto.Response.of(savedAnswer);

    }


    @Transactional
    public AddOrDeleteHelpfulRes addOrDeleteHelpful(Long answerId, String email) {

        Answer answer = answerService.getAnswer(answerId);
        User user = userService.getUserByEmail(Email.of(email));

        if (helpfulService.existsUser(answer, user)) {
            helpfulService.delete(answer, user);
            return AddOrDeleteHelpfulRes.of(false);
        }


        Helpful helpful = Helpful.createHelpful(answer, user);
        answer.addHelpful(helpful);

        helpfulService.create(helpful);

        return AddOrDeleteHelpfulRes.of(true);

    }

    @Transactional
    public DeleteAnswerRes deleteAnswer(Long answerId, String email) {

        User user = userService.getUserByEmail(Email.of(email));
        Answer answer = answerService.getAnswer(answerId);

        if (answer.getUser() != user) {
            throw new AuthenticationException(ErrorCode.ANSWER_USER_NOT_WRITER);
        }

        answerService.deleteAnswer(answer);

        return DeleteAnswerRes.of();
    }

    @Transactional
    public ReplyCreateDto.Response createReply(Long answerId, ReplyCreateDto.Request request, String email) {

        Answer answer = answerService.getAnswerFetchQuestion(answerId);
        User user = userService.getUserByEmail(Email.of(email));

        AnswerReply answerReply = request.toEntity();
        AnswerReply saveAnswerReply = AnswerReply.createReply(answerReply, user, answer);

        saveAnswerReply = answerReplyService.create(saveAnswerReply);
        answer.addReply(saveAnswerReply);

        createNotification(answer.getQuestion(), QUESTION_ANSWER_REPLY);
        fcmService.sendMessageTo(answer.getUser().getFcmToken(), answer.getQuestion().getTitle(), QUESTION_ANSWER_REPLY, ServiceType.QUESTION.getValue());

        return ReplyCreateDto.Response.of(saveAnswerReply);

    }

    public List<GetReplyRes> getReply(Long answerId) {


        Answer answer = answerService.getAnswer(answerId);
        List<AnswerReply> answerReplyList = answerReplyService.getReplyByAnswerFetchUser(answer);

        List<GetReplyRes> getReplyRes = answerReplyList.stream().map(reply ->
                GetReplyRes.of(reply)
        ).collect(Collectors.toList());
        return getReplyRes;

    }

    @Transactional
    public DeleteReplyRes deleteReply(Long replyId, String email) {

        User user = userService.getUserByEmail(Email.of(email));
        AnswerReply answerReply = answerReplyService.getReplyFetchUser(replyId);

        if (user != answerReply.getUser()) {
            throw new AuthenticationException(ErrorCode.REPLY_USER_NOT_WRITER);
        }

        answerReplyService.deleteReply(answerReply);

        return DeleteReplyRes.of();

    }

    @Transactional
    public ReplyUpdateDto.Response updateReply(ReplyUpdateDto.Request request, Long replyId, String email) {

        User user = userService.getUserByEmail(Email.of(email));
        AnswerReply answerReply = answerReplyService.getReplyFetchUser(replyId);

        if (user != answerReply.getUser()) {
            throw new AuthenticationException(ErrorCode.REPLY_USER_NOT_WRITER);
        }

        AnswerReply updateAnswerReply = request.toEntity();
        AnswerReply updatedAnswerReply = answerReplyService.updateReply(answerReply, updateAnswerReply);

        return ReplyUpdateDto.Response.of(updatedAnswerReply);

    }


    private void createNotification(Question question, String content) {
        Notification notification = Notification.createNotification(question.getTitle(), content, ServiceType.QUESTION, question.getUser(), question.getQuestionId());
        notificationService.create(notification);
    }
}
