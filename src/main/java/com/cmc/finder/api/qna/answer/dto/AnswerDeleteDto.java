package com.cmc.finder.api.qna.answer.dto;

import com.cmc.finder.api.qna.qustion.dto.QuestionDeleteDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class AnswerDeleteDto {

    private String message;

    public static AnswerDeleteDto of() {

        return AnswerDeleteDto.builder()
                .message("delete success")
                .build();
    }

}
