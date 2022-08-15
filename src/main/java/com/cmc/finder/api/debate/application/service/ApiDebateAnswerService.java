package com.cmc.finder.api.debate.application.service;

import com.cmc.finder.api.debate.dto.*;
import com.cmc.finder.domain.debate.application.DebateAnswerService;
import com.cmc.finder.domain.debate.application.DebateService;
import com.cmc.finder.domain.debate.entity.Debate;
import com.cmc.finder.domain.debate.entity.DebateAnswer;
import com.cmc.finder.domain.model.Email;
import com.cmc.finder.domain.model.ServiceType;
import com.cmc.finder.domain.notification.application.NotificationService;
import com.cmc.finder.domain.notification.entity.Notification;
import com.cmc.finder.domain.report.application.ReportService;
import com.cmc.finder.domain.report.entity.Report;
import com.cmc.finder.domain.report.exception.AlreadyReceivedReportException;
import com.cmc.finder.domain.user.entity.User;
import com.cmc.finder.domain.user.application.UserService;
import com.cmc.finder.global.advice.CheckDebateAdmin;
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
    private final ReportService reportService;
    private final FcmService fcmService;


    @Transactional
    public CreateDebateAnswerDto.Response createDebateAnswer(Long debateId, CreateDebateAnswerDto.Request request, String email) {

        Debate debate = debateService.getDebate(debateId);
        User user = userService.getUserByEmail(Email.of(email));

        DebateAnswer debateAnswer = request.toEntity();
        DebateAnswer saveDebateAnswer = DebateAnswer.createDebateAnswer(user, debate, debateAnswer);

        saveDebateAnswer = debateAnswerService.saveDebateAnswer(saveDebateAnswer);

        if (debate.getWriter().getIsActive()) {
            fcmService.sendMessageTo(debate.getWriter().getFcmToken(), debate.getTitle(), DEBATE_ANSWER, ServiceType.DEBATE.getValue());
        }
        createNotification(debate, DEBATE_ANSWER, debate.getWriter());

        return CreateDebateAnswerDto.Response.from(saveDebateAnswer);

    }


    @CheckDebateAdmin
    @Transactional
    public DeleteDebateAnswerRes deleteDebateAnswer(Long debateAnswerId, String email) {

        debateAnswerService.deleteDebateAnswer(debateAnswerId);
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

        if (debateAnswer.getUser().getIsActive()) {
            fcmService.sendMessageTo(debateAnswer.getUser().getFcmToken(), debateAnswer.getDebate().getTitle(), DEBATE_ANSWER_REPLY, Type.DEBATE.getValue());
        }
        createNotification(debateAnswer.getDebate(), DEBATE_ANSWER_REPLY, debateAnswer.getUser());

        return CreateDebateReplyDto.Response.from(saveReply);

    }


    private void createNotification(Debate debate, String content, User user) {
        Notification notification = Notification.createNotification(debate.getTitle(), content, ServiceType.DEBATE, user, debate.getDebateId());
        notificationService.create(notification);
    }


    @CheckDebateAdmin
    @Transactional
    public UpdateDebateAnswerDto.Response updateDebateAnswer(Long debateAnswerId, String email, UpdateDebateAnswerDto.Request request) {

        DebateAnswer updateDebateAnswer = request.toEntity();
        DebateAnswer updatedDebateAnswer = debateAnswerService.updateDebateAnswer(debateAnswerId, updateDebateAnswer);

        return UpdateDebateAnswerDto.Response.from(updatedDebateAnswer);

    }

    @Transactional
    public ReportDebateRes reportAnswer(Long debateAnswerId, String email) {

        DebateAnswer debateAnswer = debateAnswerService.getDebateAnswerFetchUser(debateAnswerId);
        User from = userService.getUserByEmail(Email.of(email));

        Report report = Report.createReport(ServiceType.DEBATE_ANSWER, from, debateAnswer.getUser(), debateAnswerId);

        if (reportService.alreadyReceivedReport(report)) {
            throw new AlreadyReceivedReportException(ErrorCode.ALREADY_RECEIVED_REPORT);
        }

        reportService.create(report);
        return ReportDebateRes.of();

    }

}
