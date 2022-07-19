package com.cmc.finder.api.user.dto;

import com.cmc.finder.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetNotificationActiveRes {

    private Boolean isActive;

    @Builder
    public GetNotificationActiveRes(Boolean isActive) {
        this.isActive = isActive;

    }

    public static GetNotificationActiveRes from(User user) {

        GetNotificationActiveRes response = GetNotificationActiveRes.builder()
                .isActive(user.getIsActive())
                .build();

        return response;

    }

}
