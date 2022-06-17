package com.cmc.finder.domain.answer.entity;

import com.cmc.finder.domain.user.entity.User;
import com.cmc.finder.domain.viewcount.entity.ViewCount;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "helpful")
@Getter
@NoArgsConstructor
public class Helpful {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long helpfulId;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "answer_id", nullable = false)
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
