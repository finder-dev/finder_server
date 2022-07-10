package com.cmc.finder.api.debate.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class DeleteDebateAnswerRes {

    private String message;

    public static DeleteDebateAnswerRes of() {

        return DeleteDebateAnswerRes.builder()
                .message("delete success")
                .build();
    }

}