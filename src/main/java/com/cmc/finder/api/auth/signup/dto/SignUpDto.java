package com.cmc.finder.api.auth.signup.dto;

import com.cmc.finder.api.auth.login.dto.OauthLoginDto;
import com.cmc.finder.domain.jwt.dto.TokenDto;
import com.cmc.finder.domain.user.constant.UserType;
import com.cmc.finder.domain.user.entity.User;
import com.cmc.finder.domain.model.Email;
import com.cmc.finder.domain.model.MBTI;
import com.cmc.finder.domain.model.Password;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.StringUtils;

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

        @NotNull(message = "MBTI는 필수값 입니다.")
        private MBTI mbti;

        @NotBlank(message = "닉네임은 필수값 입니다.")
        private String nickname;

        // TODO 입력 안해도 되는거 맞나?
        private String introduction;

//        private MultipartFile profileUrl;

        @Size(max = 5, message = "최대 5개까지 입력하실 수 있습니다.")
        //TODO 변경 생각
        private List<@Length(max = 8,message = "8글자를 초과할 수 없습니다.") String> keywords;

        public User toEntity(String fileName) {

            User user = User.builder()
                    .email(Email.of(this.getEmail()))
                    .password(Password.builder()
                            .value(this.password)
                            .build())
                    .mbti(this.mbti)
                    .userType(UserType.GENERAL)
                    .nickname(this.nickname)
                    .introduction(this.introduction)
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

//        private String email;
//
//        private MBTI mbti;
//
//        private String nickname;
//
//        public static Response of(User user) {
//
//            return Response.builder()
//                    .email(user.getEmail().getValue())
//                    .mbti(user.getMbti())
//                    .nickname(user.getNickname())
//                    .build();
//
//        }

    }

}
