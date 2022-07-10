package com.cmc.finder.domain.notification.repository;

import com.cmc.finder.domain.notification.entity.Notification;
import com.cmc.finder.domain.user.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Slice<Notification> findAllByUser(User user, Pageable pageable);
}
