package com.cmc.finder.api.debate.dto;

import com.cmc.finder.domain.debate.entity.Debate;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


public class CreateDebateDto {

    @Getter @Setter
    public static class Request {

        @Size(max = 50, message = "제목은 50자이내로 작성해주세요.")
        @NotBlank(message = "글 제목은 필수값 입니다.")
        private String title;

        @Size(max = 8, message = "선택지는 8자이내로 작성해주세요.")
        @NotBlank(message = "토론 선택지A는 필수값 입니다.")
        private String optionA;

        @Size(max = 8, message = "선택지는 8자이내로 작성해주세요.")
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

        public static Response from(Debate debate) {
            return CreateDebateDto.Response.builder()
                    .debateId(debate.getDebateId())
                    .build();

        }
    }
}
