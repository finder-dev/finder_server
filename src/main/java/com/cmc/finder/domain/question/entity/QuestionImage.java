package com.cmc.finder.domain.question.entity;

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
    private String imageName;

    @Column(name = "question_image_url", nullable = false)
    private String imageUrl;

    private Boolean isRep;

    @Builder
    public QuestionImage(Question question, String imageName, String imageUrl, Boolean isRep) {
        this.question = question;
        this.imageName = imageName;
        this.imageUrl = imageUrl;
        this.isRep = isRep;
    }

    public static QuestionImage createQuestionImage(Question question, String imageName, String imageUrl, Boolean isRep) {
        return QuestionImage.builder()
                .question(question)
                .imageName(imageName)
                .imageUrl(imageUrl)
                .isRep(isRep)
                .build();
    }

}
