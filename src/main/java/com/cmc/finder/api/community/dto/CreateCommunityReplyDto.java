package com.cmc.finder.api.community.dto;

import com.cmc.finder.domain.community.entity.CommunityAnswer;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


public class CreateCommunityReplyDto {

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
    public static class Response {

        private Long communityReplyId;

        public static Response from(CommunityAnswer communityAnswer) {
            return Response.builder()
                    .communityReplyId(communityAnswer.getID())
                    .build();

        }
    }
}
