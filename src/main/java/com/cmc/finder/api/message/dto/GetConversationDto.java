package com.cmc.finder.api.message.dto;

import com.cmc.finder.domain.message.entity.Message;
import com.cmc.finder.global.util.DateTimeUtils;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class GetConversationDto {

    @Getter
    @Setter
    public static class Request {

        @NotNull(message = "보낸 유저의 아이디는 필수값 입니다.")
        private Long userId;

    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {

        private Long fromUserId;

        private Long toUserId;

        private String content;

        private String createTime;


        public static Response of(Message message) {

            Response response = Response.builder()
                    .fromUserId(message.getFrom().getId())
                    .toUserId(message.getFrom() == message.getOwner() ? message.getOther().getId() : message.getOwner().getId())
                    .content(message.getContent())
                    .createTime(DateTimeUtils.convertToLocalDatetimeToTime(message.getCreateTime()))
                    .build();

            return response;

        }
    }

}
