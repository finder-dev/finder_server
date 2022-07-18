package com.cmc.finder.api.user.dto;

import com.cmc.finder.domain.message.entity.Message;
import com.cmc.finder.domain.user.entity.User;
import com.cmc.finder.global.util.DateTimeUtils;
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

    public static GetNotificationActiveRes of(User user) {

        GetNotificationActiveRes response = GetNotificationActiveRes.builder()
                .isActive(user.getIsActive())
                .build();

        return response;

    }

}
