package com.cmc.finder.api.debate.application;

import com.cmc.finder.api.debate.dto.*;
import com.cmc.finder.domain.debate.application.DebateAnswerReplyService;
import com.cmc.finder.domain.debate.application.DebateAnswerService;
import com.cmc.finder.domain.debate.application.DebateService;
import com.cmc.finder.domain.debate.entity.Debate;
import com.cmc.finder.domain.debate.entity.DebateAnswer;
import com.cmc.finder.domain.debate.entity.DebateAnswerReply;
import com.cmc.finder.domain.model.Email;
import com.cmc.finder.domain.model.Type;
import com.cmc.finder.domain.notification.application.NotificationService;
import com.cmc.finder.domain.notification.entity.Notification;
import com.cmc.finder.domain.user.entity.User;
import com.cmc.finder.domain.user.service.UserService;
import com.cmc.finder.global.error.exception.AuthenticationException;
import com.cmc.finder.global.error.exception.ErrorCode;
import com.cmc.finder.infra.notification.FcmService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.cmc.finder.global.util.Constants.DEBATE_ANSWER;
import static com.cmc.finder.global.util.Constants.DEBATE_ANSWER_REPLY;


@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ApiDebateAnswerService {

    private final DebateService debateService;
    private final DebateAnswerService debateAnswerService;
    private final UserService userService;
    private final NotificationService notificationService;
    private final DebateAnswerReplyService debateAnswerReplyService;
    private final FcmService fcmService;


    @Transactional
    public DebateAnswerCreateDto.Response createDebateAnswer(Long debateId, DebateAnswerCreateDto.Request request, String email) {

        Debate debate = debateService.getDebate(debateId);
        User user = userService.getUserByEmail(Email.of(email));

        DebateAnswer debateAnswer = request.toEntity();
        DebateAnswer saveDebateAnswer = DebateAnswer.createDebateAnswer(user, debate, debateAnswer);

        saveDebateAnswer = debateAnswerService.saveDebateAnswer(saveDebateAnswer);

        //TODO fcm은 이후 작업으로..

        // fcmService.sendMessageTo(debate.getWriter().getFcmToken(), debate.getTitle(), DEBATE_ANSWER, Type.DEBATE.getValue());
        createNotification(debate, DEBATE_ANSWER);

        return DebateAnswerCreateDto.Response.of(saveDebateAnswer);

    }


    @Transactional
    public DebateAnswerDeleteDto deleteDebateAnswer(Long debateAnswerId, String email) {

        User user = userService.getUserByEmail(Email.of(email));
        DebateAnswer debateAnswer = debateAnswerService.getDebateAnswer(debateAnswerId);

        // 유저 검증
        if (debateAnswer.getUser() != user) {
            throw new AuthenticationException(ErrorCode.DEBATE_ANSWER_USER_NOT_WRITER);
        }

        debateAnswerService.deleteDebateAnswer(debateAnswer);

        return DebateAnswerDeleteDto.of();

    }

    @Transactional
    public DebateReplyCreateDto.Response createDebateReply(Long debateAnswerId, DebateReplyCreateDto.Request request, String email) {

        DebateAnswer debateAnswer = debateAnswerService.getDebateAnswer(debateAnswerId);
        User user = userService.getUserByEmail(Email.of(email));

        DebateAnswerReply debateAnswerReply = request.toEntity();
        DebateAnswerReply saveDebateAnswerReply = DebateAnswerReply.createDebateReply(debateAnswerReply, user, debateAnswer);

        saveDebateAnswerReply = debateAnswerReplyService.create(saveDebateAnswerReply);
        debateAnswer.addDebateReply(saveDebateAnswerReply);

        //TODO fcm은 이후 작업으로..

        // fcmService.sendMessageTo(debateAnswer.getUser().getFcmToken(), debateAnswer.getDebate().getTitle(), DEBATE_ANSWER_REPLY, Type.DEBATE.getValue());
        createNotification(debateAnswer.getDebate(), DEBATE_ANSWER_REPLY);

        return DebateReplyCreateDto.Response.of(saveDebateAnswerReply);

    }

    @Transactional
    public DeleteDebateReplyRes deleteDebateReply(Long debateReplyId, String email) {

        User user = userService.getUserByEmail(Email.of(email));
        DebateAnswerReply debateAnswerReply = debateAnswerReplyService.getDebateReplyFetchUser(debateReplyId);

        if (user != debateAnswerReply.getUser()) {
            throw new AuthenticationException(ErrorCode.DEBATE_REPLY_USER_NOT_WRITER);
        }

        debateAnswerReplyService.deleteDebateReply(debateAnswerReply);

        return DeleteDebateReplyRes.of();

    }

    @Transactional
    public DebateReplyUpdateDto.Response updateDebateReply(DebateReplyUpdateDto.Request request, Long debateReplyId, String email) {

        User user = userService.getUserByEmail(Email.of(email));
        DebateAnswerReply debateAnswerReply = debateAnswerReplyService.getDebateReplyFetchUser(debateReplyId);

        if (user != debateAnswerReply.getUser()) {
            throw new AuthenticationException(ErrorCode.DEBATE_REPLY_USER_NOT_WRITER);
        }

        DebateAnswerReply updateDebateAnswerReply = request.toEntity();
        DebateAnswerReply updatedDebateAnswerReply = debateAnswerReplyService.updateDebateReply(debateAnswerReply, updateDebateAnswerReply);

        return DebateReplyUpdateDto.Response.of(updatedDebateAnswerReply);

    }

    private void createNotification(Debate debate, String content) {
        Notification notification = Notification.createNotification(debate.getTitle(), content, Type.DEBATE, debate.getWriter(), debate.getDebateId());
        notificationService.create(notification);
    }


}
