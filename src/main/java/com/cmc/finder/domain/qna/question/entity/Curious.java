package com.cmc.finder.domain.qna.question.entity;

import com.cmc.finder.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "curious")
@Getter
@NoArgsConstructor
public class Curious {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CURIOUS_ID")
    private Long Id;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "QUESTION_ID", nullable = false)
    private Question question;

    @Builder
    public Curious(Question question, User user) {
        this.question = question;
        this.user = user;
    }

    public static Curious createCurious(Question question, User user) {
        return Curious.builder()
                .question(question)
                .user(user)
                .build();
    }

    public void setQuestion(Question question) {
        this.question = question;
    }


}
