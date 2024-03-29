package com.cmc.finder.domain.qna.question.entity;

import com.cmc.finder.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "view_count")
@Getter
@NoArgsConstructor
public class ViewCount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VIEW_COUNT_ID")
    private Long Id;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "QUESTION_ID", nullable = false)
    private Question question;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Builder
    public ViewCount(Question question, User user) {
        this.question = question;
        this.user = user;
    }

    public static ViewCount createViewCount(Question question, User user) {
        return ViewCount.builder()
                .question(question)
                .user(user)
                .build();
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
