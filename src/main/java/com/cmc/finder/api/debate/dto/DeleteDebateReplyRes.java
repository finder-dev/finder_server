package com.cmc.finder.api.debate.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class DeleteDebateReplyRes {

    private String message;

    public static DeleteDebateReplyRes of() {

        return DeleteDebateReplyRes.builder()
                .message("delete success")
                .build();
    }

}