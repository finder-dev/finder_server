package com.cmc.finder.api.auth.login.dto;

import com.cmc.finder.domain.jwt.dto.TokenDto;
import com.cmc.finder.domain.model.MBTI;
import com.cmc.finder.domain.user.constant.UserType;
import com.cmc.finder.global.validator.Enum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

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

        @NotBlank(message = "MBTI는 필수값 입니다.")
        @Enum(enumClass = MBTI.class, message ="잘못된 Enum 값 입니다.")
        private String mbti;

        @NotBlank(message = "닉네임은 필수값 입니다.")
        private String nickname;

        private MultipartFile profileImg;

        @Size(max = 4, message = "최대 4개까지 입력하실 수 있습니다.")
        private List<@Length(min=1, max = 6,message = "1자 이상 6자 이하의 태그만 가능합니다.") String> keywords;

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
