package com.cmc.finder.api.qna.qustion.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class AddOrDeleteFavoriteQuestionRes {

    private String message;

    public static AddOrDeleteFavoriteQuestionRes of(Boolean addOrDelete) {

        // add
        if (addOrDelete) {
            return AddOrDeleteFavoriteQuestionRes.builder()
                    .message("add success")
                    .build();
        }
        // delete
        else {
            return AddOrDeleteFavoriteQuestionRes.builder()
                    .message("delete success")
                    .build();
        }


    }
}
