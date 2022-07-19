package com.cmc.finder.api.auth.login.dto;

import com.cmc.finder.domain.jwt.dto.TokenDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.Date;

public class OauthLoginDto {

    @Getter
    @Setter
    public static class Request {

        @NotBlank(message = "소셜 타입은 필수값 입니다.")
        private String userType;

        private String mbti;

        private String nickname;

        //TODO 삭제
        private MultipartFile profileImg;

//        @NotBlank(message = "FCM Token은 필수값 입니다.")
        private String fcmToken;

    }

    @Getter @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {

        private String grantType;

        private String accessToken;

        @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
        private Date accessTokenExpireTime;

        private String refreshToken;

        @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
        private Date refreshTokenExpireTime;

        public static Response from(TokenDto tokenDto) {
            return Response.builder()
                    .grantType(tokenDto.getGrantType())
                    .accessToken(tokenDto.getAccessToken())
                    .accessTokenExpireTime(tokenDto.getAccessTokenExpireTime())
                    .refreshToken(tokenDto.getRefreshToken())
                    .refreshTokenExpireTime(tokenDto.getRefreshTokenExpireTime())
                    .build();
        }

    }

}
