package com.cmc.finder.api.community.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class AddOrDeleteLikeRes {

    private String message;

    public static AddOrDeleteLikeRes of(Boolean addOrDelete) {

        // add
        if (addOrDelete) {
            return AddOrDeleteLikeRes.builder()
                    .message("add success")
                    .build();
        }
        // delete
        return AddOrDeleteLikeRes.builder()
                .message("delete success")
                .build();

    }
}
