package com.cmc.finder.api.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;


public class UpdateProfileImgDto {

    @Getter @Setter
    public static class Request {

        @NotNull(message = "이미지는 필수값 입니다.")
        private MultipartFile profileImg;

    }

    @Builder
    @Getter @Setter
    public static class Response {

        private String profileUrl;

        public static Response of(String url) {

            return Response.builder()
                    .profileUrl(url)
                    .build();
        }
    }



}
