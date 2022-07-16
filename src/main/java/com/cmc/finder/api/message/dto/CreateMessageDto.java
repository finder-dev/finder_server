package com.cmc.finder.api.message.dto;

import lombok.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


public class CreateMessageDto {

    @Getter @Setter
    public static class Request {

        @NotNull(message = "보낼 유저의 아이디는 필수값 입니다.")
        private Long toUserId;

//        @Size(min = 10, max = 500, message = "10자 이상 500자 이하로 작성해주세요.")
        @NotBlank(message = "메시지 내용은 필수값 입니다.")
        private String content;

    }

    @Getter @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {

        private String message;

        public static Response of() {

            return Response.builder()
                    //TODO 상수로 묶기
                    .message("메시지 전송에 성공하였습니다.")
                    .build();

        }

    }
}
