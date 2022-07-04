package com.cmc.finder.api.community.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class DeleteCommunityAnswerRes {

    private String message;

    public static DeleteCommunityAnswerRes of() {

        return DeleteCommunityAnswerRes.builder()
                .message("delete success")
                .build();
    }

}