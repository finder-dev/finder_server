package com.cmc.finder.api.debate.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class ReportDebateRes {

    private String message;

    public static ReportDebateRes of() {

        return ReportDebateRes.builder()
                .message("report success")
                .build();

    }
}
