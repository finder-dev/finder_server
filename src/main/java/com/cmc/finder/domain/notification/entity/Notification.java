package com.cmc.finder.domain.notification.entity;

import com.cmc.finder.domain.model.Type;
import com.cmc.finder.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "notification")
@Getter
@NoArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Type type;

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
    public Notification(Long notificationId, Type type, String title, String content, User user, Long serviceId) {
        this.notificationId = notificationId;
        this.type = type;
        this.title = title;
        this.content = content;
        this.user = user;
        this.serviceId = serviceId;
    }

    public static Notification createNotification(String title, String content, Type type, User user, Long serviceId) {

        return Notification.builder()
                .title(title)
                .content(content)
                .type(type)
                .user(user)
                .serviceId(serviceId)
                .build();
    }

}
