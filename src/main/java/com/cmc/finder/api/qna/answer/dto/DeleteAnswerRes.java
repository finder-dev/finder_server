package com.cmc.finder.api.qna.answer.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class DeleteAnswerRes {

    private String message;

    public static DeleteAnswerRes of() {

        return DeleteAnswerRes.builder()
                .message("delete success")
                .build();
    }

}
