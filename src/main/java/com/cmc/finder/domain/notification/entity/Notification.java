package com.cmc.finder.domain.notification.entity;

import com.cmc.finder.domain.base.BaseTimeEntity;
import com.cmc.finder.domain.model.ServiceType;
import com.cmc.finder.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "notification")
@Getter
@NoArgsConstructor
public class Notification extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ServiceType serviceType;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Long serviceId;

    @Builder
    public Notification(ServiceType serviceType, String title, String content, User user, Long serviceId) {
        this.serviceType = serviceType;
        this.title = title;
        this.content = content;
        this.user = user;
        this.serviceId = serviceId;
    }

    public static Notification createNotification(String title, String content, ServiceType serviceType, User user, Long serviceId) {

        return Notification.builder()
                .title(title)
                .content(content)
                .serviceType(serviceType)
                .user(user)
                .serviceId(serviceId)
                .build();
    }

}
