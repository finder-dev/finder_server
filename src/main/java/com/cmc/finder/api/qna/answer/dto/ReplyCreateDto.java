package com.cmc.finder.api.qna.answer.dto;

import com.cmc.finder.domain.qna.answer.entity.AnswerReply;
import lombok.*;

import javax.validation.constraints.NotBlank;

public class ReplyCreateDto {

    @Getter
    @Setter
    public static class Request {

        @NotBlank(message = "글 내용은 필수값 입니다.")
        private String content;

        public AnswerReply toEntity() {
            return AnswerReply.builder()
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

        public static Response of(AnswerReply answerReply) {

            return Response.builder()
                    .replyId(answerReply.getReplyId())
                    .build();

        }
    }

}
