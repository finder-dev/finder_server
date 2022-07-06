package com.cmc.finder.domain.notification.application;

import com.cmc.finder.domain.notification.entity.Notification;
import com.cmc.finder.domain.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public void create(Notification notification) {
        notificationRepository.save(notification);

    }

}
