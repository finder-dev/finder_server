package com.cmc.finder.api.auth.logout.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class LogoutResponse {

    private String message;

    public static LogoutResponse of() {

        return LogoutResponse.builder()
                .message("logout success")
                .build();

    }
}
