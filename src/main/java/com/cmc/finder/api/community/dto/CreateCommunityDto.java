package com.cmc.finder.api.community.dto;

import com.cmc.finder.domain.community.entity.Community;
import com.cmc.finder.domain.model.MBTI;
import com.cmc.finder.global.validator.Enum;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


public class CreateCommunityDto {

    @Getter @Setter
    public static class Request {

        @NotBlank(message = "글 제목은 필수값 입니다.")
        private String title;

        @Size(min = 10, max = 500, message = "10자 이상 500자 이하로 작성해주세요.")
        @NotBlank(message = "글 내용은 필수값 입니다.")
        private String content;

        @NotBlank(message = "MBTI는 필수값 입니다.")
        @Enum(enumClass = MBTI.class, message ="잘못된 Enum 값 입니다.")
        private String mbti;

        @NotNull(message = "질문 여부는 필수값 입니다.")
        private Boolean isQuestion;

        @Size(max = 10, message = "최대 10개까지 사진을 추가하실 수 있습니다.")
        private List<MultipartFile> communityImages = new ArrayList<>();

        public Community toEntity() {

            return Community.builder()
                    .title(title)
                    .content(content)
                    .isQuestion(isQuestion)
                    .mbti(MBTI.from(mbti))
                    .build();
        }
    }

    @Getter @Setter
    @Builder
    public static class Response {

        private Long communityId;

        public static Response from(Community community) {

            return Response.builder()
                    .communityId(community.getId())
                    .build();

        }

    }
}
