package com.cmc.finder.api.message.dto;

import com.cmc.finder.domain.user.entity.User;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter @Setter
@Builder
public class ReportUserDto {

    @Getter @Setter
    public static class Request {

        @NotNull(message = "신고할 유저의 아이디는 필수값 입니다.")
        private Long reportUserId;

    }

    @Getter @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {

        private String message;

        public static Response of() {

            return Response.builder()
                    .message("report success")
                    .build();

        }

    }

}
