package com.cmc.finder.api.auth.login.dto;

import com.cmc.finder.domain.jwt.dto.TokenDto;
import com.cmc.finder.domain.model.MBTI;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

public class OauthLoginDto {

    @Getter
    @Setter
    public static class Request {

        private String userType;

        @NotNull(message = "MBTI는 필수값 입니다.")
        private MBTI mbti;

        @NotBlank(message = "닉네임은 필수값 입니다.")
        private String nickname;

        private String profileUrl;

        @Size(max = 5, message = "최대 5개까지 입력하실 수 있습니다.")
        private List<String> keywords;

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

        public static Response of(TokenDto tokenDto) {
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
