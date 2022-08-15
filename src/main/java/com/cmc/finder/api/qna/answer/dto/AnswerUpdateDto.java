package com.cmc.finder.api.qna.answer.dto;

import com.cmc.finder.domain.qna.answer.entity.Answer;
import com.cmc.finder.domain.qna.answer.entity.AnswerReply;
import lombok.*;

import javax.validation.constraints.NotBlank;

public class AnswerUpdateDto {

    @Getter
    @Setter
    public static class Request {

        @NotBlank(message = "글 내용은 필수값 입니다.")
        private String content;

        public Answer toEntity() {
            return Answer.builder()
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

        public static Response of(AnswerReply answerReply) {

            return Response.builder()
                    .replyId(answerReply.getId())
                    .content(answerReply.getContent())
                    .build();

        }
    }

}
