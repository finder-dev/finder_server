package com.cmc.finder.api.auth.logout.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class LogoutRequestDto {

    private String message;

    public static LogoutRequestDto of() {

        return LogoutRequestDto.builder()
                .message("logout success")
                .build();

    }
}
