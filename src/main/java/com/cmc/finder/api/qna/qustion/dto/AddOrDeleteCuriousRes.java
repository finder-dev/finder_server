package com.cmc.finder.api.qna.qustion.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class AddOrDeleteCuriousRes {

    private String message;

    public static AddOrDeleteCuriousRes of(Boolean addOrDelete) {

        // add
        if (addOrDelete) {
            return AddOrDeleteCuriousRes.builder()
                    .message("add success")
                    .build();
        }
        // delete
        return AddOrDeleteCuriousRes.builder()
                .message("delete success")
                .build();

    }
}
