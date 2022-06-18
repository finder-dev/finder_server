package com.cmc.finder.api.auth.signup.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class NicknameCheckDto {

    @Getter @Setter
    public static class Request {

        @NotBlank
        @Size(min = 1, max = 6, message = "닉네임은 6자 이내로 적어주세요.")
        private String nickname;

    }

    @Builder
    @Getter @Setter
    public static class Response {

        private String message;

        public static Response of() {

            return Response.builder()
                    .message("사용 가능한 닉네임입니다.")
                    .build();

        }
    }

}
