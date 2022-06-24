package com.cmc.finder.api.qna.qustion.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class QuestionDeleteDto {

    private String message;

    public static QuestionDeleteDto of() {

        return QuestionDeleteDto.builder()
                .message("delete success")
                .build();
    }

}