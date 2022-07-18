package com.cmc.finder.api.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NotificationOnOffRes {

    private String message;

    public static NotificationOnOffRes of(Boolean onOff) {

        if (onOff) {
            return NotificationOnOffRes.builder()
                    .message("on success")
                    .build();
        }

        return NotificationOnOffRes.builder()
                .message("off success")
                .build();
    }
}
