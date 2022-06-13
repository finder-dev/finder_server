package com.cmc.finder.api.qna.qustion.dto;

import com.cmc.finder.domain.model.MBTI;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class QuestionSimpleDto {

    private Long questionId;

    private String title;

    private String content;

    private String imageUrl;

    private String userNickname;

    private MBTI userMBTI;

//    private int answerCount;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @QueryProjection
    public QuestionSimpleDto(Long questionId, String title, String content, String userNickname, String imageUrl,
                             MBTI userMBTI, LocalDateTime createTime) {

        this.questionId = questionId;
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.userNickname = userNickname;
        this.userMBTI = userMBTI;
//        this.answerCount = answerCount;
        this.createTime = createTime;
    }

}
