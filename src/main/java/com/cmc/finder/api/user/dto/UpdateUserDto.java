package com.cmc.finder.api.user.dto;

import com.cmc.finder.domain.community.entity.CommunityAnswer;
import com.cmc.finder.domain.model.MBTI;
import com.cmc.finder.domain.user.entity.User;
import com.cmc.finder.global.validator.Enum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

public class UpdateUserDto {

    @Getter
    @Setter
    public static class Request {

        @NotBlank(message = "MBTI는 필수값 입니다.")
        @Enum(enumClass = MBTI.class, message ="잘못된 Enum 값 입니다.")
        private String mbti;

        @NotBlank(message = "닉네임은 필수값 입니다.")
        private String nickname;

        public User toEntity() {
            return User.builder()
                    .mbti(MBTI.from(mbti))
                    .nickname(nickname)
                    .build();
        }

    }

    @Builder
    @Getter @Setter
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
