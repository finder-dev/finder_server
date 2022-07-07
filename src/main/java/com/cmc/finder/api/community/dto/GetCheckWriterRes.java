package com.cmc.finder.api.community.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
public class GetCheckWriterRes {

    private Boolean isWriter;

    public static GetCheckWriterRes of(Boolean check) {

        return GetCheckWriterRes.builder()
                .isWriter(check)
                .build();

    }



}
