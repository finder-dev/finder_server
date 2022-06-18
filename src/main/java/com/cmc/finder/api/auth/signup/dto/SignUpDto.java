package com.cmc.finder.api.auth.signup.dto;

import com.cmc.finder.api.auth.login.dto.OauthLoginDto;
import com.cmc.finder.domain.jwt.dto.TokenDto;
import com.cmc.finder.domain.user.constant.UserType;
import com.cmc.finder.domain.user.entity.User;
import com.cmc.finder.domain.model.Email;
import com.cmc.finder.domain.model.MBTI;
import com.cmc.finder.domain.model.Password;
import com.cmc.finder.global.validator.Enum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

public class SignUpDto {

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class Request {

        @NotBlank(message = "이메일은 필수값 입니다.")
        @javax.validation.constraints.Email(message = "이메일 형식에 맞지 않습니다.")
        private String email;

        @NotBlank(message = "비밀번호는 필수값 입니다.")
        private String password;

        @NotBlank(message = "MBTI는 필수값 입니다.")
        @Enum(enumClass = MBTI.class, message ="잘못된 Enum 값 입니다.")
        private String mbti;

        @Length(max = 6, message = "닉네임은 6자 이내로 적어주세요.")
        @NotBlank(message = "닉네임은 필수값 입니다.")
        private String nickname;


        @Size(max = 4, message = "최대 4개까지 입력하실 수 있습니다.")
        //TODO 변경 생각
        private List<@Length(min=1, max = 6,message = "1자 이상 6자 이하의 태그만 가능합니다.") String> keywords;

        private MultipartFile profileImg;

        public User toEntity(String fileName) {

            User user = User.builder()
                    .email(Email.of(this.getEmail()))
                    .password(Password.builder()
                            .value(this.password)
                            .build())
                    .mbti(MBTI.from(this.mbti))
                    .userType(UserType.GENERAL)
                    .nickname(this.nickname)
                    .profileImg("default.png")
                    .build();

            if (StringUtils.hasText(fileName)) {
                user.updateProfileUrl(fileName);
            }

            return user;

        }


    }

    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {

        private String grantType;

        private String accessToken;

        @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
        private Date accessTokenExpireTime;

        private String refreshToken;

        @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
        private Date refreshTokenExpireTime;

        public static SignUpDto.Response of(TokenDto tokenDto) {
            return SignUpDto.Response.builder()
                    .grantType(tokenDto.getGrantType())
                    .accessToken(tokenDto.getAccessToken())
                    .accessTokenExpireTime(tokenDto.getAccessTokenExpireTime())
                    .refreshToken(tokenDto.getRefreshToken())
                    .refreshTokenExpireTime(tokenDto.getRefreshTokenExpireTime())
                    .build();
        }


    }

}
