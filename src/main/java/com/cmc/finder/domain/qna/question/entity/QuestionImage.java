package com.cmc.finder.domain.qna.question.entity;

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
    @Column(name = "QUESTION_IMAGE_ID")
    private Long Id;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "QUESTION_ID", nullable = false)
    private Question question;

    @Column(name = "QUESTION_IMAGE_NAME", nullable = false)
    private String imageName;

    @Column(name = "QUESTION_IMAGE_URL", nullable = false)
    private String imageUrl;

    @Builder
    public QuestionImage(Question question, String imageName, String imageUrl) {
        this.question = question;
        this.imageName = imageName;
        this.imageUrl = imageUrl;
    }

    public static QuestionImage createQuestionImage(Question question, String imageName, String imageUrl) {
        return QuestionImage.builder()
                .question(question)
                .imageName(imageName)
                .imageUrl(imageUrl)
                .build();
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
