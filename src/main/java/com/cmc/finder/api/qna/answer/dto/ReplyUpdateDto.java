package com.cmc.finder.api.qna.answer.dto;

import com.cmc.finder.domain.qna.answer.entity.Reply;
import lombok.*;

import javax.validation.constraints.NotBlank;

public class ReplyUpdateDto {

    @Getter
    @Setter
    public static class Request {

        @NotBlank(message = "글 내용은 필수값 입니다.")
        private String content;

        public Reply toEntity() {
            return Reply.builder()
                    .content(content)
                    .build();
        }

    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {

        private Long replyId;

        private String content;

        public static Response of(Reply reply) {

            return Response.builder()
                    .replyId(reply.getReplyId())
                    .content(reply.getContent())
                    .build();

        }
    }

}
