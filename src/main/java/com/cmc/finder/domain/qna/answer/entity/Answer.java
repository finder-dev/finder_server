package com.cmc.finder.domain.qna.answer.entity;

import com.cmc.finder.domain.base.BaseTimeEntity;
import com.cmc.finder.domain.qna.question.entity.Question;
import com.cmc.finder.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "answer")
@Getter
@NoArgsConstructor
public class Answer extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;

    @Column(nullable = false)
    private String title;

    @Lob
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
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;


    @OneToMany(
            mappedBy = "answer",
            cascade = CascadeType.ALL
    )
    private List<AnswerImage> answerImages = new ArrayList<>();

    @OneToMany(
            mappedBy = "answer",
            cascade = CascadeType.ALL
    )
    private List<Helpful> helpfuls = new ArrayList<>();

    @OneToMany(
            mappedBy = "answer",
            cascade = CascadeType.ALL
    )
    private List<AnswerReply> replies = new ArrayList<>();

    public void addReply(AnswerReply answerReply) {
        replies.add(answerReply);
        answerReply.setAnswer(this);
    }


    public void addHelpful(Helpful helpful) {
        helpfuls.add(helpful);
        helpful.setAnswer(this);
    }

    public void addAnswerImage(AnswerImage answerImage) {
        answerImages.add(answerImage);
        answerImage.setAnswer(this);
    }


    @Builder
    public Answer(String title, String content, User user, Question question) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.question = question;
    }

    public static Answer createAnswer(Answer answer, User user, Question question) {
        return Answer.builder()
                .title(answer.title)
                .content(answer.content)
                .user(user)
                .question(question)
                .build();
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
