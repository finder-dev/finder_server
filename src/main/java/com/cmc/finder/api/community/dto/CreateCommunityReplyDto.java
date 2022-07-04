package com.cmc.finder.api.community.dto;

import com.cmc.finder.domain.community.entity.CommunityAnswer;
import lombok.*;

import javax.validation.constraints.NotBlank;


public class CreateCommunityReplyDto {

    @Getter @Setter
    public static class Request {

        //TODO 글자 수
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

        private Long communityReplyId;

        public static Response of(CommunityAnswer communityAnswer) {
            return Response.builder()
                    .communityReplyId(communityAnswer.getCommunityAnswerId())
                    .build();

        }
    }
}
