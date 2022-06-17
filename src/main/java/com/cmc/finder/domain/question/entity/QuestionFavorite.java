package com.cmc.finder.domain.question.entity;

import com.cmc.finder.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "question_favorite")
@Getter
@NoArgsConstructor
public class QuestionFavorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionFavoriteId;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Builder
    public QuestionFavorite(Question question, User user) {
        this.question = question;
        this.user = user;
    }

    public static QuestionFavorite createQuestionFavorite(Question question, User user) {
        return QuestionFavorite.builder()
                .question(question)
                .user(user)
                .build();
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

}
