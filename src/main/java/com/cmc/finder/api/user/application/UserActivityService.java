package com.cmc.finder.api.user.application;

import com.cmc.finder.api.user.dto.*;
import com.cmc.finder.domain.community.application.CommunityService;
import com.cmc.finder.domain.community.application.SaveCommunityService;
import com.cmc.finder.domain.community.entity.Community;
import com.cmc.finder.domain.community.entity.SaveCommunity;
import com.cmc.finder.domain.message.application.MessageService;
import com.cmc.finder.domain.message.entity.Message;
import com.cmc.finder.domain.model.Email;
import com.cmc.finder.domain.notification.application.NotificationService;
import com.cmc.finder.domain.notification.entity.Notification;
import com.cmc.finder.domain.user.entity.User;
import com.cmc.finder.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserActivityService {

    private final UserService userService;
    private final SaveCommunityService saveCommunityService;
    private final CommunityService communityService;
    private final NotificationService notificationService;
    private final MessageService messageService;

    public Slice<GetUserActivityRes> getSaveCommunity(String email, Pageable pageable) {

        User user = userService.getUserByEmail(Email.of(email));
        Slice<SaveCommunity> saveCommunityFetchCommunity = saveCommunityService.getSaveCommunityFetchCommunity(user, pageable);

        List<GetUserActivityRes> res = saveCommunityFetchCommunity.stream().map(saveCommunity ->
                GetUserActivityRes.of(saveCommunity.getCommunity(), user)).collect(Collectors.toList());

        return new SliceImpl<>(res, pageable, saveCommunityFetchCommunity.hasNext());

    }

    public Slice<GetUserActivityRes> getCommunityByUser(String email, Pageable pageable) {

        User user = userService.getUserByEmail(Email.of(email));
        Slice<Community> communityList = communityService.getCommunityByUser(user, pageable);

        List<GetUserActivityRes> res = communityList.stream().map(community -> GetUserActivityRes.of(community, user)).collect(Collectors.toList());
        return new SliceImpl<>(res, pageable, communityList.hasNext());


    }

    public Slice<GetUserActivityRes> getCommunityByCommentUser(String email, Pageable pageable) {

        User user = userService.getUserByEmail(Email.of(email));
        Slice<Community> communityList = communityService.getCommunityByCommentUser(user, pageable);

        List<GetUserActivityRes> res = communityList.stream().map(community -> GetUserActivityRes.of(community, user)).collect(Collectors.toList());
        return new SliceImpl<>(res, pageable, communityList.hasNext());

    }

    public Slice<GetNotificationRes> getNotification(String email, Pageable pageable) {

        User user = userService.getUserByEmail(Email.of(email));
        Slice<Notification> notificaitonList = notificationService.getNotificaitonList(user, pageable);

        List<GetNotificationRes> res = notificaitonList.stream().map(GetNotificationRes::from).collect(Collectors.toList());

        return new SliceImpl<>(res, pageable, notificaitonList.hasNext());

    }
    public Slice<GetMessageRes> getMessageByFromUser(String email, Pageable pageable) {

        User user = userService.getUserByEmail(Email.of(email));
        Slice<Message> messages = messageService.getMessageByFrom(user, pageable);

        List<GetMessageRes> res = messages.stream().map(GetMessageRes::from).collect(Collectors.toList());

        return new SliceImpl<>(res, pageable, messages.hasNext());

    }



}
