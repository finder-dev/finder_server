package com.cmc.finder.api.userinfo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class UpdateProfileDto {

    private String profileUrl;

    public static UpdateProfileDto of(String url) {

        return UpdateProfileDto.builder()
                .profileUrl(url)
                .build();
    }
}
