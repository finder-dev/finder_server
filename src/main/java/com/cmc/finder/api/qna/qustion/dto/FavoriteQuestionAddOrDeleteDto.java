package com.cmc.finder.api.qna.qustion.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class FavoriteQuestionAddOrDeleteDto {

    private String message;

    public static FavoriteQuestionAddOrDeleteDto of(Boolean addOrDelete) {

        // add
        if (addOrDelete) {
            return FavoriteQuestionAddOrDeleteDto.builder()
                    .message("add success")
                    .build();
        }
        // delete
        else {
            return FavoriteQuestionAddOrDeleteDto.builder()
                    .message("delete success")
                    .build();
        }


    }
}
