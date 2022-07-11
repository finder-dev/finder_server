package com.cmc.finder.api.debate.dto;

import com.cmc.finder.domain.debate.entity.Debate;
import lombok.*;

import javax.validation.constraints.NotBlank;


public class CreateDebateDto {

    @Getter @Setter
    public static class Request {

        @NotBlank(message = "글 제목은 필수값 입니다.")
        private String title;

        @NotBlank(message = "토론 선택지A는 필수값 입니다.")
        private String optionA;

        @NotBlank(message = "토론 선택지B는 필수값 입니다.")
        private String optionB;


        public Debate toEntity() {

            return Debate.builder()
                    .title(title)
                    .optionA(optionA)
                    .optionB(optionB)
                    .build();

        }
    }

    @Getter @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {

        private Long debateId;

        public static Response of(Debate debate) {
            return CreateDebateDto.Response.builder()
                    .debateId(debate.getDebateId())
                    .build();

        }
    }
}