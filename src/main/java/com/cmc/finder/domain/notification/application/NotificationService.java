package com.cmc.finder.domain.notification.application;

import com.cmc.finder.domain.notification.entity.Notification;
import com.cmc.finder.domain.notification.repository.NotificationRepository;
import com.cmc.finder.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @Transactional
    public void create(Notification notification) {
        notificationRepository.save(notification);
    }

    public Slice<Notification> getNotificaitonList(User user, Pageable pageable) {

        Slice<Notification> notifications = notificationRepository.findAllByUser(user, pageable);
        return notifications;
    }



}
