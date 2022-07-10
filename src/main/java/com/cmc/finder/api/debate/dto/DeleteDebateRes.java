package com.cmc.finder.api.debate.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class DeleteDebateRes {

    private String message;

    public static DeleteDebateRes of() {

        return DeleteDebateRes.builder()
                .message("delete success")
                .build();
    }

}