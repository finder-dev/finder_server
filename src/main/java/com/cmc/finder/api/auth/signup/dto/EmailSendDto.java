package com.cmc.finder.api.auth.signup.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class EmailSendDto {

    @Getter @Setter
    public static class Request {

        @NotBlank(message = "이메일은 필수값 입니다.")
        @Email
        private String email;

    }

    @Builder
    @Getter @Setter
    public static class Response {

        private String message;

        public static Response of() {

            return Response.builder()
                    .message("메일을 전송하였습니다.")
                    .build();

        }
    }


}
