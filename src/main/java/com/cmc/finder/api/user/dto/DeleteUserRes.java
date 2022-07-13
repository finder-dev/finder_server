package com.cmc.finder.api.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class DeleteUserRes {

    private String message;

    public static DeleteUserRes of() {

        return DeleteUserRes.builder()
                .message("delete success")
                .build();
    }

}