package com.cmc.finder.domain.answer.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "answer_image")
@Getter
@NoArgsConstructor
public class AnswerImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerImgId;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "answer_id", nullable = false)
    private Answer answer;

    @Column(name = "answer_image_filename", nullable = false)
    private String imageName;

    @Column(name = "answer_image_url", nullable = false)
    private String imageUrl;

    @Builder
    public AnswerImage(Answer answer, String imageName, String imageUrl) {
        this.answer = answer;
        this.imageName = imageName;
        this.imageUrl = imageUrl;
    }

    public static AnswerImage createAnswerImage(Answer answer, String imageName, String imageUrl) {
        return AnswerImage.builder()
                .answer(answer)
                .imageName(imageName)
                .imageUrl(imageUrl)
                .build();
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }
}
