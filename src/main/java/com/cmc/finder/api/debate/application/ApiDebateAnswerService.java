package com.cmc.finder.api.debate.application;

import com.cmc.finder.api.debate.dto.*;
import com.cmc.finder.domain.debate.application.DebateAnswerService;
import com.cmc.finder.domain.debate.application.DebateService;
import com.cmc.finder.domain.debate.entity.Debate;
import com.cmc.finder.domain.debate.entity.DebateAnswer;
import com.cmc.finder.domain.model.Email;
import com.cmc.finder.domain.model.ServiceType;
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

import static com.cmc.finder.global.util.Constants.*;


@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ApiDebateAnswerService {

    private final DebateService debateService;
    private final DebateAnswerService debateAnswerService;
    private final UserService userService;
    private final NotificationService notificationService;
    private final FcmService fcmService;


    @Transactional
    public CreateDebateAnswerDto.Response createDebateAnswer(Long debateId, CreateDebateAnswerDto.Request request, String email) {

        Debate debate = debateService.getDebate(debateId);
        User user = userService.getUserByEmail(Email.of(email));

        DebateAnswer debateAnswer = request.toEntity();
        DebateAnswer saveDebateAnswer = DebateAnswer.createDebateAnswer(user, debate, debateAnswer);

        saveDebateAnswer = debateAnswerService.saveDebateAnswer(saveDebateAnswer);

        //TODO fcm은 이후 작업으로..

        // fcmService.sendMessageTo(debate.getWriter().getFcmToken(), debate.getTitle(), DEBATE_ANSWER, Type.DEBATE.getValue());
        createNotification(debate, DEBATE_ANSWER);

        return CreateDebateAnswerDto.Response.of(saveDebateAnswer);

    }


    @Transactional
    public DeleteDebateAnswerRes deleteDebateAnswer(Long debateAnswerId, String email) {

        User user = userService.getUserByEmail(Email.of(email));
        DebateAnswer debateAnswer = debateAnswerService.getDebateAnswerFetchUser(debateAnswerId);

        // 유저 검증
        if (debateAnswer.getUser() != user) {
            throw new AuthenticationException(ErrorCode.DEBATE_ANSWER_USER_NOT_WRITER);
        }

        debateAnswerService.deleteDebateAnswer(debateAnswer);

        return DeleteDebateAnswerRes.of();

    }


    @Transactional
    public CreateDebateReplyDto.Response createDebateReply(Long debateAnswerId, CreateDebateReplyDto.Request request, String email) {

        User user = userService.getUserByEmail(Email.of(email));
        DebateAnswer debateAnswer = debateAnswerService.getDebateAnswer(debateAnswerId);

        DebateAnswer reply = request.toEntity();
        DebateAnswer saveReply = DebateAnswer.createDebateAnswer(user, debateAnswer.getDebate(), reply);

        saveReply = debateAnswerService.saveDebateAnswer(saveReply);
        saveReply.setParent(debateAnswer);

        //TODO fcm은 이후 작업으로..

        // fcmService.sendMessageTo(debateAnswer.getUser().getFcmToken(), debateAnswer.getDebate().getTitle(), DEBATE_ANSWER_REPLY, Type.DEBATE.getValue());
        createNotification(debateAnswer.getDebate(), DEBATE_ANSWER_REPLY);

        return CreateDebateReplyDto.Response.of(saveReply);

    }


    private void createNotification(Debate debate, String content) {
        Notification notification = Notification.createNotification(debate.getTitle(), content, ServiceType.DEBATE, debate.getWriter(), debate.getDebateId());
        notificationService.create(notification);
    }


    @Transactional
    public UpdateDebateAnswerDto.Response updateDebateAnswer(UpdateDebateAnswerDto.Request request, Long debateAnswerId, String email) {

        User user = userService.getUserByEmail(Email.of(email));
        DebateAnswer debateAnswer = debateAnswerService.getDebateAnswerFetchUser(debateAnswerId);

        if (user != debateAnswer.getUser()) {
            throw new AuthenticationException(ErrorCode.DEBATE_ANSWER_USER_NOT_WRITER);
        }

        DebateAnswer updateDebateAnswer = request.toEntity();
        DebateAnswer updatedDebateAnswer = debateAnswerService.updateDebateAnswer(debateAnswer, updateDebateAnswer);

        return UpdateDebateAnswerDto.Response.of(updatedDebateAnswer);

    }

}
