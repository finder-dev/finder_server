package com.cmc.finder.domain.notification.entity;

import com.cmc.finder.domain.debate.entity.Debate;
import com.cmc.finder.domain.model.Email;
import com.cmc.finder.domain.model.MBTI;
import com.cmc.finder.domain.model.Password;
import com.cmc.finder.domain.notification.constant.NotificationType;
import com.cmc.finder.domain.qna.question.entity.Question;
import com.cmc.finder.domain.user.constant.UserType;
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
    private NotificationType notificationType;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "debate_id")
    private Debate debate;


    @Builder
    public Notification(Long notificationId, NotificationType notificationType, String title, String content, User user, Question question, Debate debate) {
        this.notificationId = notificationId;
        this.notificationType = notificationType;
        this.title = title;
        this.content = content;
        this.user = user;
        this.question = question;
        this.debate = debate;
    }

    public static Notification createNotification(String title, String content, NotificationType notificationType, User user, Question question, Debate debate) {

        return Notification.builder()
                .title(title)
                .content(content)
                .notificationType(notificationType)
                .user(user)
                .question(question)
                .debate(debate)
                .build();
    }

}
