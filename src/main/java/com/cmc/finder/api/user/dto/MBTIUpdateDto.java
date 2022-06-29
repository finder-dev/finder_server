package com.cmc.finder.api.user.dto;

import com.cmc.finder.domain.model.MBTI;
import com.cmc.finder.global.validator.Enum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

public class MBTIUpdateDto {

    @Getter
    @Setter
    public static class Request {

        @NotBlank(message = "MBTI는 필수값 입니다.")
        @Enum(enumClass = MBTI.class, message ="잘못된 Enum 값 입니다.")
        private String mbti;

    }

    @Builder
    @Getter @Setter
    public static class Response {

        private MBTI mbti;

        public static Response of(MBTI mbti) {

            return Response.builder()
                    .mbti(mbti)
                    .build();

        }
    }
}
