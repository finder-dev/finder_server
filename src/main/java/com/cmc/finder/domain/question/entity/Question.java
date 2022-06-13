package com.cmc.finder.domain.question.entity;

import com.cmc.finder.domain.base.BaseTimeEntity;
import com.cmc.finder.domain.model.MBTI;
import com.cmc.finder.domain.user.entity.User;
import com.cmc.finder.domain.viewcount.entity.ViewCount;
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
    private List<ViewCount> viewCount = new ArrayList<>();

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


    public void addQuestionImage(QuestionImage questionImage) {
        questionImages.add(questionImage);
        questionImage.setQuestion(this);
    }

    @Builder
    public Question(String title,String content, MBTI mbti, User user) {
        this.title = title;
        this.content = content;
        this.mbti = mbti;
        this.user = user;
    }

    public static Question createQuestion(String title,String content, MBTI mbti, User user) {
        return Question.builder()
                .title(title)
                .content(content)
                .mbti(mbti)
                .user(user)
                .build();
    }

}
