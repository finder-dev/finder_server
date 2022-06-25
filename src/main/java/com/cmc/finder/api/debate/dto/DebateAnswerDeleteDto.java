package com.cmc.finder.api.debate.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class DebateAnswerDeleteDto {

    private String message;

    public static DebateAnswerDeleteDto of() {

        return DebateAnswerDeleteDto.builder()
                .message("delete success")
                .build();
    }

}