package com.cmc.finder.api.qna.qustion.dto;

import com.cmc.finder.domain.qna.question.entity.Question;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class QuestionHotDto {

    private Long questionId;

    private String title;

    public static QuestionHotDto of(Question question) {
        return QuestionHotDto.builder()
                .questionId(question.getQuestionId())
                .title(question.getTitle())
                .build();

    }

}
