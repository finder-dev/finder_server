package com.cmc.finder.api.community.application;

import com.cmc.finder.api.community.dto.*;
import com.cmc.finder.domain.community.application.CommunityAnswerService;
import com.cmc.finder.domain.community.application.CommunityService;
import com.cmc.finder.domain.community.entity.Community;
import com.cmc.finder.domain.community.entity.CommunityAnswer;
import com.cmc.finder.domain.model.Email;
import com.cmc.finder.domain.model.Type;
import com.cmc.finder.domain.notification.entity.Notification;
import com.cmc.finder.domain.notification.application.NotificationService;
import com.cmc.finder.domain.user.entity.User;
import com.cmc.finder.domain.user.service.UserService;
import com.cmc.finder.global.error.exception.AuthenticationException;
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

    @Transactional
    public CreateCommunityAnswerDto.Response createCommunityAnswer(Long communityId, CreateCommunityAnswerDto.Request request, String email) {

        Community community = communityService.getCommunity(communityId);
        User user = userService.getUserByEmail(Email.of(email));

        CommunityAnswer communityAnswer = request.toEntity();
        CommunityAnswer saveCommunityAnswer = CommunityAnswer.createCommunityAnswer(user, community, communityAnswer);

        saveCommunityAnswer = communityAnswerService.saveCommunityAnswer(saveCommunityAnswer);

        fcmService.sendMessageTo(community.getUser().getFcmToken(), community.getTitle(), COMMUNITY_ANSWER, Type.COMMUNITY.getValue());
        createNotification(community, COMMUNITY_ANSWER);

        return CreateCommunityAnswerDto.Response.of(saveCommunityAnswer);

    }


    private void createNotification(Community community, String content) {
        Notification notification = Notification.createNotification(community.getTitle(), content, Type.COMMUNITY, community.getUser(), community.getCommunityId());
        notificationService.create(notification);
    }

    @Transactional
    public DeleteCommunityAnswerRes deleteAnswer(Long answerId, String email) {

        User user = userService.getUserByEmail(Email.of(email));
        CommunityAnswer communityAnswer = communityAnswerService.getCommunityAnswerFetchUser(answerId);

        if (communityAnswer.getUser() != user) {
            throw new AuthenticationException(ErrorCode.ANSWER_USER_NOT_WRITER);
        }

        communityAnswerService.deleteCommunityAnswer(communityAnswer);
        return DeleteCommunityAnswerRes.of();

    }

    @Transactional
    public UpdateCommunityAnswerDto.Response updateAnswer(UpdateCommunityAnswerDto.Request request, Long answerId, String email) {

        User user = userService.getUserByEmail(Email.of(email));
        CommunityAnswer communityAnswer = communityAnswerService.getCommunityAnswerFetchUser(answerId);

        if (user != communityAnswer.getUser()) {
            throw new AuthenticationException(ErrorCode.ANSWER_USER_NOT_WRITER);
        }

        CommunityAnswer updateCommunityAnswer = request.toEntity();
        CommunityAnswer updatedCommunityAnswer = communityAnswerService.updateCommunityAnswer(communityAnswer, updateCommunityAnswer);

        return UpdateCommunityAnswerDto.Response.of(updatedCommunityAnswer);
    }

    @Transactional
    public CreateCommunityReplyDto.Response createCommunityReply(Long answerId, CreateCommunityReplyDto.Request request, String email) {

        User user = userService.getUserByEmail(Email.of(email));
        CommunityAnswer communityAnswer = communityAnswerService.getCommunityAnswerFetchUser(answerId);

        CommunityAnswer reply = request.toEntity();
        CommunityAnswer saveReply = CommunityAnswer.createCommunityAnswer(user, communityAnswer.getCommunity(), reply);

        saveReply = communityAnswerService.saveCommunityAnswer(saveReply);
        saveReply.setParent(communityAnswer);

        fcmService.sendMessageTo(communityAnswer.getUser().getFcmToken(), communityAnswer.getCommunity().getTitle(), COMMUNITY_ANSWER_REPLY, Type.COMMUNITY.getValue());
        createNotification(communityAnswer.getCommunity(), COMMUNITY_ANSWER_REPLY);

        return CreateCommunityReplyDto.Response.of(saveReply);
    }


    public GetCheckWriterRes checkWriter(Long answerId, String email) {

        User user = userService.getUserByEmail(Email.of(email));
        Boolean check = communityAnswerService.isCommunityAnswerWriter(answerId, user);

        return GetCheckWriterRes.of(check);

    }

}
