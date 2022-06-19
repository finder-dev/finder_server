package com.cmc.finder.api.userinfo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotBlank;

public class UpdateNicknameDto {

    @Getter
    @Setter
    public static class Request {

        @NotBlank(message = "닉네임은 필수값 입니다.")
        private String nickname;

    }

    @Builder
    @Getter @Setter
    public static class Response {

        @NotBlank
        private String nickname;

        public static Response of(String nickname) {

            return Response.builder()
                    .nickname(nickname)
                    .build();

        }
    }
}
