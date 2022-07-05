package com.cmc.finder.api.community.dto;

import com.cmc.finder.domain.community.entity.CommunityAnswer;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


public class CreateCommunityAnswerDto {

    @Getter @Setter
    public static class Request {

        @Size(max = 300, message = "300자 이하로 작성해주세요.")
        @NotBlank(message = "글 내용은 필수값 입니다.")
        private String content;

        public CommunityAnswer toEntity() {
            return CommunityAnswer.builder()
                    .content(content)
                    .build();

        }
    }

    @Getter @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {

        private Long communityAnswerId;

        public static Response of(CommunityAnswer communityAnswer) {
            return Response.builder()
                    .communityAnswerId(communityAnswer.getCommunityAnswerId())
                    .build();

        }
    }
}