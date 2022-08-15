package com.cmc.finder.api.auth.logout.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class LogoutRes {

    private String message;

    public static LogoutRes of() {

        return LogoutRes.builder()
                .message("logout success")
                .build();

    }
}
