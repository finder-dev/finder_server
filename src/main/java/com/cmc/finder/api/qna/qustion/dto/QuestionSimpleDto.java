package com.cmc.finder.api.qna.qustion.dto;

import com.cmc.finder.domain.model.MBTI;
import com.cmc.finder.domain.question.entity.Question;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class QuestionSimpleDto {

    private Long questionId;

    private String title;

    private String content;

    private String imageUrl;

    private String userNickname;

    private MBTI userMBTI;

    private Integer answerCount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Builder
    public QuestionSimpleDto(Long questionId, String title, String content, String imageUrl,
                             String userNickname, MBTI userMBTI,Integer answerCount, LocalDateTime createTime) {
        this.questionId = questionId;
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.userNickname = userNickname;
        this.userMBTI = userMBTI;
        this.answerCount = answerCount;
        this.createTime = createTime;
    }

    public static QuestionSimpleDto of(Question question) {

        return QuestionSimpleDto.builder()
                .questionId(question.getQuestionId())
                .title(question.getTitle())
                .content(question.getContent())
                .imageUrl(question.getQuestionImages().get(0).getImageUrl())
                .userNickname(question.getUser().getNickname())
                .userMBTI(question.getUser().getMbti())
                .answerCount(question.getAnswers().size())
                .createTime(question.getCreateTime())
                .build();

    }

}
