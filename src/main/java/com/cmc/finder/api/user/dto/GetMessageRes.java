package com.cmc.finder.api.user.dto;

import com.cmc.finder.domain.message.entity.Message;
import com.cmc.finder.global.util.DateTimeUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GetMessageRes {

    private Long toUserId;

    private String userNickname;

    private String content;

    private String createTime;

    @Builder
    public GetMessageRes(Long toUserId, String userNickname, String content, String createTime) {

        this.toUserId = toUserId;
        this.userNickname = userNickname;
        this.content = content;
        this.createTime = createTime;

    }

    public static GetMessageRes of(Message message) {

        GetMessageRes response = GetMessageRes.builder()
                .toUserId(message.getTo().getUserId())
                .userNickname(message.getTo().getNickname())
                .content(message.getContent())
                .createTime(DateTimeUtils.convertToLocalDatetimeToTime(message.getCreateTime()))
                .build();

        return response;

    }



}
