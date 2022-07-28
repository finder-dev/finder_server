package com.cmc.finder.api.community.application.service;

import com.cmc.finder.api.community.dto.*;
import com.cmc.finder.domain.community.application.CommunityAnswerService;
import com.cmc.finder.domain.community.application.CommunityService;
import com.cmc.finder.domain.community.entity.Community;
import com.cmc.finder.domain.community.entity.CommunityAnswer;
import com.cmc.finder.domain.model.Email;
import com.cmc.finder.domain.model.ServiceType;
import com.cmc.finder.domain.notification.entity.Notification;
import com.cmc.finder.domain.notification.application.NotificationService;
import com.cmc.finder.domain.report.application.ReportService;
import com.cmc.finder.domain.report.entity.Report;
import com.cmc.finder.domain.report.exception.AlreadyReceivedReportException;
import com.cmc.finder.domain.user.entity.User;
import com.cmc.finder.domain.user.application.UserService;
import com.cmc.finder.global.advice.CheckCommunityAdmin;
import com.cmc.finder.global.error.exception.ErrorCode;
import com.cmc.finder.infra.notification.FcmService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.cmc.finder.global.util.Constants.COMMUNITY_ANSWER;
import static com.cmc.finder.global.util.Constants.COMMUNITY_ANSWER_REPLY;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ApiCommunityAnswerService {

    private final UserService userService;
    private final CommunityService communityService;
    private final CommunityAnswerService communityAnswerService;
    private final FcmService fcmService;
    private final NotificationService notificationService;
    private final ReportService reportService;

    @Transactional
    public CreateCommunityAnswerDto.Response createCommunityAnswer(Long communityId, CreateCommunityAnswerDto.Request request, String email) {

        Community community = communityService.getCommunity(communityId);
        User user = userService.getUserByEmail(Email.of(email));

        CommunityAnswer communityAnswer = request.toEntity();
        CommunityAnswer saveCommunityAnswer = CommunityAnswer.createCommunityAnswer(user, community, communityAnswer);

        saveCommunityAnswer = communityAnswerService.saveCommunityAnswer(saveCommunityAnswer);

        //TODO fcm은 이후 작업으로..
//        if (community.getUser().getIsActive()) {
//             fcmService.sendMessageTo(community.getUser().getFcmToken(), community.getTitle(), COMMUNITY_ANSWER, Type.COMMUNITY.getValue());
//        }
        createNotification(community, COMMUNITY_ANSWER, community.getUser());

        return CreateCommunityAnswerDto.Response.from(saveCommunityAnswer);

    }


    private void createNotification(Community community, String content, User user) {
        Notification notification = Notification.createNotification(community.getTitle(), content, ServiceType.COMMUNITY, user, community.getCommunityId());
        notificationService.create(notification);
    }

    @CheckCommunityAdmin
    @Transactional
    public DeleteCommunityAnswerRes deleteAnswer(Long answerId, String email) {

        communityAnswerService.deleteCommunityAnswer(answerId);
        return DeleteCommunityAnswerRes.of();

    }

    @CheckCommunityAdmin
    @Transactional
    public UpdateCommunityAnswerDto.Response updateAnswer(Long answerId, String email, UpdateCommunityAnswerDto.Request request) {

        CommunityAnswer updateCommunityAnswer = request.toEntity();
        CommunityAnswer updatedCommunityAnswer = communityAnswerService.updateCommunityAnswer(answerId, updateCommunityAnswer);

        return UpdateCommunityAnswerDto.Response.from(updatedCommunityAnswer);
    }

    @Transactional
    public CreateCommunityReplyDto.Response createCommunityReply(Long answerId, CreateCommunityReplyDto.Request request, String email) {

        User user = userService.getUserByEmail(Email.of(email));
        CommunityAnswer communityAnswer = communityAnswerService.getCommunityAnswerFetchUser(answerId);

        CommunityAnswer reply = request.toEntity();
        CommunityAnswer saveReply = CommunityAnswer.createCommunityAnswer(user, communityAnswer.getCommunity(), reply);

        saveReply = communityAnswerService.saveCommunityAnswer(saveReply);
        saveReply.setParent(communityAnswer);

        //TODO fcm은 이후 작업으로..
//        if (communityAnswer.getUser().getIsActive()) {
//            fcmService.sendMessageTo(communityAnswer.getUser().getFcmToken(), communityAnswer.getCommunity().getTitle(), COMMUNITY_ANSWER_REPLY, Type.COMMUNITY.getValue());
//        }
        createNotification(communityAnswer.getCommunity(), COMMUNITY_ANSWER_REPLY, communityAnswer.getUser());

        return CreateCommunityReplyDto.Response.from(saveReply);
    }

    @Transactional
    public ReportCommunityRes reportAnswer(Long answerId, String email) {
        CommunityAnswer communityAnswer = communityAnswerService.getCommunityAnswerFetchUser(answerId);
        User from = userService.getUserByEmail(Email.of(email));

        Report report = Report.createReport(ServiceType.COMMUNITY_ANSWER, from, communityAnswer.getUser(), answerId);

        if (reportService.alreadyReceivedReport(report)) {
            throw new AlreadyReceivedReportException(ErrorCode.ALREADY_RECEIVED_REPORT);
        }

        reportService.create(report);
        return ReportCommunityRes.of();

    }
}
