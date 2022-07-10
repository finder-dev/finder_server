package com.cmc.finder.api.debate.dto;

import com.cmc.finder.domain.debate.entity.DebateAnswer;
import lombok.*;

import javax.validation.constraints.NotBlank;


public class CreateDebateAnswerDto {

    @Getter @Setter
    public static class Request {

        //TODO 글자 수
        @NotBlank(message = "글 내용은 필수값 입니다.")
        private String content;

        public DebateAnswer toEntity() {
            return DebateAnswer.builder()
                    .content(content)
                    .build();

        }
    }

    @Getter @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {

        private Long debateAnswerId;

        public static Response of(DebateAnswer debateAnswer) {
            return Response.builder()
                    .debateAnswerId(debateAnswer.getDebateAnswerId())
                    .build();

        }
    }
}
