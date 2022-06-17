package com.cmc.finder.api.qna.qustion.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class QuestionFavoriteAddOrDeleteDto {

    private String message;

    public static QuestionFavoriteAddOrDeleteDto of(Boolean addOrDelete) {

        // add
        if (addOrDelete) {
            return QuestionFavoriteAddOrDeleteDto.builder()
                    .message("add success")
                    .build();
        }
        // delete
        return QuestionFavoriteAddOrDeleteDto.builder()
                .message("delete success")
                .build();

    }
}
