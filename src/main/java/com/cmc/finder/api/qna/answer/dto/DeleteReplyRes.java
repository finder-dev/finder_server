package com.cmc.finder.api.qna.answer.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class DeleteReplyRes {

    private String message;

    public static DeleteReplyRes of() {

        return DeleteReplyRes.builder()
                .message("delete success")
                .build();
    }

}