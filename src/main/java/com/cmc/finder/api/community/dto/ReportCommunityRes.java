package com.cmc.finder.api.community.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class ReportCommunityRes {

    private String message;

    public static ReportCommunityRes of() {

        return ReportCommunityRes.builder()
                .message("report success")
                .build();

    }
}
