package com.cmc.finder.domain.question.entity;

import com.cmc.finder.domain.model.MBTI;
import com.cmc.finder.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "question_image")
@Getter
@NoArgsConstructor
public class QuestionImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionImgId;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(name = "question_image_filename", nullable = false)
    private String filename;

    @Builder
    public QuestionImage(Question question, String filename) {
        this.question = question;
        this.filename = filename;
    }

    public static QuestionImage createQuestionImage(Question question, String filename) {
        return QuestionImage.builder()
                .question(question)
                .filename(filename)
                .build();
    }

}
