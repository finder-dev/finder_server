package com.cmc.finder.api.debate.dto;

import com.cmc.finder.domain.debate.entity.DebateAnswer;
import lombok.*;

import javax.validation.constraints.NotBlank;

public class CreateDebateReplyDto {

    @Getter
    @Setter
    public static class Request {

        @NotBlank(message = "글 내용은 필수값 입니다.")
        private String content;

        public DebateAnswer toEntity() {
            return DebateAnswer.builder()
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

        public static Response from(DebateAnswer debateAnswer) {

            return Response.builder()
                    .debateReplyId(debateAnswer.getDebateAnswerId())
                    .build();

        }
    }

}
