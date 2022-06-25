package com.cmc.finder.api.debate.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class DebateDeleteDto {

    private String message;

    public static DebateDeleteDto of() {

        return DebateDeleteDto.builder()
                .message("delete success")
                .build();
    }

}