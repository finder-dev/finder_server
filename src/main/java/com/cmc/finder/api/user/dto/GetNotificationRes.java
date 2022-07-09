package com.cmc.finder.api.user.dto;

import com.cmc.finder.domain.model.Type;
import com.cmc.finder.domain.notification.entity.Notification;
import com.cmc.finder.global.util.DateTimeUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetNotificationRes {

    private String title;

    private String content;

    private Long serviceId;

    private Type serviceType;

    private String createTime;

    @Builder
    public GetNotificationRes(String title, String content, Long serviceId, Type serviceType, String createTime) {
        this.title = title;
        this.content = content;
        this.serviceId = serviceId;
        this.serviceType = serviceType;
        this.createTime = createTime;
    }

    public static GetNotificationRes of(Notification notification) {

        GetNotificationRes response = GetNotificationRes.builder()
                .title(notification.getTitle())
                .content(notification.getContent())
                .serviceId(notification.getServiceId())
                .serviceType(notification.getType())
                .createTime(DateTimeUtils.convertToLocalDatetimeToTime(notification.getCreateTime()))
                .build();

        return response;

    }
}


