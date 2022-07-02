package com.cmc.finder.domain.qna.question.entity;

import com.cmc.finder.domain.qna.answer.entity.Answer;
import com.cmc.finder.domain.base.BaseTimeEntity;
import com.cmc.finder.domain.model.MBTI;
import com.cmc.finder.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "question")
@Getter
@NoArgsConstructor
public class Question extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private MBTI mbti;

    @OneToMany(
            mappedBy = "question",
            cascade = CascadeType.ALL
    )
    private List<ViewCount> viewCounts = new ArrayList<>();

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(
            mappedBy = "question",
            cascade = CascadeType.ALL
    )
    private List<QuestionImage> questionImages = new ArrayList<>();

    @OneToMany(
            mappedBy = "question",
            cascade = CascadeType.ALL
    )
    private List<Answer> answers = new ArrayList<>();

    @OneToMany(
            mappedBy = "question",
            cascade = CascadeType.ALL
    )
    private List<Curious> curiousList = new ArrayList<>();

    public void addViewCount(ViewCount viewCount) {
        viewCounts.add(viewCount);
        viewCount.setQuestion(this);
    }

    public void addQuestionImage(QuestionImage questionImage) {
        questionImages.add(questionImage);
        questionImage.setQuestion(this);
    }

    public void addAnswer(Answer answer) {
        answers.add(answer);
        answer.setQuestion(this);
    }

    public void addCurious(Curious curious) {
        curiousList.add(curious);
        curious.setQuestion(this);
    }

    @Builder
    public Question(String title,String content, MBTI mbti, User user) {
        this.title = title;
        this.content = content;
        this.mbti = mbti;
        this.user = user;
    }

    public static Question createQuestion(Question question, User user) {
        return Question.builder()
                .title(question.title)
                .content(question.content)
                .mbti(question.mbti)
                .user(user)
                .build();
    }

    public void updateQuestion(Question updatequestion) {

        this.title = updatequestion.getTitle();
        this.content = updatequestion.getContent();
        this.mbti = updatequestion.getMbti();

    }

    public void deleteQuestionImage(QuestionImage questionImage) {
        this.questionImages.remove(questionImage);
    }
}
