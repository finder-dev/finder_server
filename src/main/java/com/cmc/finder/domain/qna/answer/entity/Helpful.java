package com.cmc.finder.domain.qna.answer.entity;

import com.cmc.finder.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "helpful")
@Getter
@NoArgsConstructor
public class Helpful {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HELPFUL_ID")
    private Long Id;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "ANSWER_ID", nullable = false)
    private Answer answer;

    @Builder
    public Helpful(Answer answer, User user) {
        this.answer = answer;
        this.user = user;
    }

    public static Helpful createHelpful(Answer answer, User user) {
        return Helpful.builder()
                .answer(answer)
                .user(user)
                .build();
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }
}
