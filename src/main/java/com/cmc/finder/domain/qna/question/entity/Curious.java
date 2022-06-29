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
    private Long curiousId;

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

    @Entity
    @Table(name = "view_count")
    @Getter
    @NoArgsConstructor
    public static class ViewCount {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long viewCountId;

        @ManyToOne(
                fetch = FetchType.LAZY
        )
        @JoinColumn(name = "question_id", nullable = false)
        private Question question;

        @ManyToOne(
                fetch = FetchType.LAZY
        )
        @JoinColumn(name = "user_id", nullable = false)
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
}
