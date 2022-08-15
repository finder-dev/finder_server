package com.cmc.finder.api.message.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.cmc.finder.global.util.Constants.SUCCESS_MESSAGE_SEND;


public class CreateMessageDto {

    @Getter
    @Setter
    public static class Request {

        @NotNull(message = "보낼 유저의 아이디는 필수값 입니다.")
        private Long toUserId;

        @Size(min = 10, max = 500, message = "10자 이상 500자 이하로 작성해주세요.")
        @NotBlank(message = "메시지 내용은 필수값 입니다.")
        private String content;

    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {

        private String message;

        public static Response of() {

            return Response.builder()
                    .message(SUCCESS_MESSAGE_SEND)
                    .build();

        }

    }
}
