package com.cmc.finder.api.user.dto;

import com.cmc.finder.domain.model.MBTI;
import com.cmc.finder.domain.model.Password;
import com.cmc.finder.domain.user.entity.User;
import com.cmc.finder.global.validator.Enum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class UpdateUserDto {

    @Getter
    @Setter
    public static class Request {

        @NotBlank(message = "MBTI는 필수값 입니다.")
        @Enum(enumClass = MBTI.class, message = "잘못된 Enum 값 입니다.")
        private String mbti;

        @Length(max = 6, message = "닉네임은 6자 이내로 적어주세요.")
        @NotBlank(message = "닉네임은 필수값 입니다.")
        private String nickname;

        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9]).{8,16}$", message = "비밀번호는 영문, 숫자를 포함하여 8 ~ 16자입니다.")
        private String password;

        public User toEntity() {

            if (password != null) {
                return User.builder()
                        .mbti(MBTI.from(mbti))
                        .nickname(nickname)
                        .password(Password.builder()
                                .value(this.password)
                                .build())
                        .build();
            } else {
                return User.builder()
                        .mbti(MBTI.from(mbti))
                        .nickname(nickname)
                        .build();
            }

        }

    }

    @Builder
    @Getter
    @Setter
    public static class Response {

        private MBTI mbti;

        private String nickname;

        public static Response of(User user) {

            return Response.builder()
                    .mbti(user.getMbti())
                    .nickname(user.getNickname())
                    .build();

        }
    }
}
