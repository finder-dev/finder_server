package com.cmc.finder.api.debate.dto;

import com.cmc.finder.domain.debate.entity.DebateAnswerReply;
import com.cmc.finder.domain.qna.answer.entity.AnswerReply;
import lombok.*;

import javax.validation.constraints.NotBlank;

public class DebateReplyCreateDto {

    @Getter
    @Setter
    public static class Request {

        @NotBlank(message = "글 내용은 필수값 입니다.")
        private String content;

        public DebateAnswerReply toEntity() {
            return DebateAnswerReply.builder()
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

        private Long debateReplyId;

        public static Response of(DebateAnswerReply debateAnswerReply) {

            return Response.builder()
                    .debateReplyId(debateAnswerReply.getDebateReplyId())
                    .build();

        }
    }

}
