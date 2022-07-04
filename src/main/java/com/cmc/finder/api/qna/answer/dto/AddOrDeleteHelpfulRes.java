package com.cmc.finder.api.qna.answer.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class AddOrDeleteHelpfulRes {

    private String message;

    public static AddOrDeleteHelpfulRes of(Boolean addOrDelete) {

        // add
        if (addOrDelete) {
            return AddOrDeleteHelpfulRes.builder()
                    .message("add success")
                    .build();
        }
        // delete
        return AddOrDeleteHelpfulRes.builder()
                .message("delete success")
                .build();

    }
}


