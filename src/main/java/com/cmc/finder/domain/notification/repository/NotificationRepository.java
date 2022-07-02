package com.cmc.finder.domain.notification.repository;

import com.cmc.finder.domain.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
