package com.cmc.finder.api.qna.qustion.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class DeleteQuestionRes {

    private String message;

    public static DeleteQuestionRes of() {

        return DeleteQuestionRes.builder()
                .message("delete success")
                .build();
    }

}