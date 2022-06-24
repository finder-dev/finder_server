package com.cmc.finder.api.qna.answer.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class HelpfulAddOrDeleteDto {

    private String message;

    public static HelpfulAddOrDeleteDto of(Boolean addOrDelete) {

        // add
        if (addOrDelete) {
            return HelpfulAddOrDeleteDto.builder()
                    .message("add success")
                    .build();
        }
        // delete
        return HelpfulAddOrDeleteDto.builder()
                .message("delete success")
                .build();

    }
}


