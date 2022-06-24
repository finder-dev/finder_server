package com.cmc.finder.api.debate.dto;

import com.cmc.finder.domain.debate.constant.Option;
import com.cmc.finder.domain.debate.entity.Debate;
import com.cmc.finder.domain.debate.entity.Debater;
import com.cmc.finder.domain.model.MBTI;
import com.cmc.finder.global.validator.Enum;
import lombok.*;

import javax.validation.constraints.NotBlank;


public class JoinDebateDto {

    @Getter @Setter
    public static class Request {

        @Enum(enumClass = Option.class, message ="잘못된 Enum 값 입니다.")
        @NotBlank(message = "선택지는 필수값 입니다.")
        private String option;


    }

    @Getter @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {

        private String message;

        private String debateTitle;

        private String nickname;

        private String option;

        public static Response of(Debater debater, Boolean join) {

            if (join) {
                return Response.builder()
                        .message("join success")
                        .debateTitle(debater.getDebate().getTitle())
                        .nickname(debater.getUser().getNickname())
                        .option(debater.getOption().name())
                        .build();
            }else {
                return Response.builder()
                        .message("detach success")
                        .debateTitle(debater.getDebate().getTitle())
                        .nickname(debater.getUser().getNickname())
                        .option(debater.getOption().name())
                        .build();
            }


        }
    }
}
