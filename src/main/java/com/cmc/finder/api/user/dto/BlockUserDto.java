package com.cmc.finder.api.user.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter @Setter
@Builder
public class BlockUserDto {

    @Getter @Setter
    public static class Request {

        @NotNull(message = "유저 아이디는 필수값 입니다.")
        private Long blockUserId;

    }

    @Getter @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {

        private String message;

        public static Response of() {

            return Response.builder()
                    .message("block success")
                    .build();
        }

    }


}