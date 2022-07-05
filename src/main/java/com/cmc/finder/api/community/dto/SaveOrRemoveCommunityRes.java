package com.cmc.finder.api.community.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class SaveOrRemoveCommunityRes {

    private String message;

    public static SaveOrRemoveCommunityRes of(Boolean saveOrRemove) {

        // save
        if (saveOrRemove) {
            return SaveOrRemoveCommunityRes.builder()
                    .message("save success")
                    .build();
        }
        // remove
        else {
            return SaveOrRemoveCommunityRes.builder()
                    .message("remove success")
                    .build();
        }


    }
}
