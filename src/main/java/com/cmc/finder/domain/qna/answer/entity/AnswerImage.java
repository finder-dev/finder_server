package com.cmc.finder.domain.qna.answer.entity;

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
    @Column(name = "ANSWER_IMAGE_ID")
    private Long Id;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "ANSWER_ID", nullable = false)
    private Answer answer;

    @Column(name = "ANSWER_IMAGE_NAME", nullable = false)
    private String imageName;

    @Column(name = "ANSWER_IMAGE_URL", nullable = false)
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
