package com.cmc.finder.api.community.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class DeleteCommunityRes {

    private String message;

    public static DeleteCommunityRes of() {

        return DeleteCommunityRes.builder()
                .message("delete success")
                .build();
    }

}