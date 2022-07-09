package com.cmc.finder.api.debate.dto;

import com.cmc.finder.domain.debate.constant.DebateState;
import com.cmc.finder.domain.debate.entity.Debate;
import com.cmc.finder.global.util.DateTimeUtils;
import com.cmc.finder.global.validator.Enum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


public class DebateSimpleDto {

    @Getter
    @Setter
    public static class Request {

        @Enum(enumClass = DebateState.class, message = "잘못된 Enum 값 입니다.", ignoreCase = true)
        private String state;

    }

    @Getter
    @Setter
    public static class Response {

        private Long debateId;

        private String title;

        private Integer joinCount;

        private DebateState debateState;

        private String deadline;

        @Builder
        public Response(Long debateId, String title, Integer joinCount, DebateState debateState, LocalDateTime deadline) {
            this.debateId = debateId;
            this.title = title;
            this.joinCount = joinCount;
            this.debateState = debateState;
            this.deadline = DateTimeUtils.convertToLocalDateTimeToDeadline(deadline);
        }

        public static Response of(Debate debate) {

            return Response.builder()
                    .debateId(debate.getDebateId())
                    .title(debate.getTitle())
                    .joinCount(debate.getDebaters().size())
                    .debateState(debate.getState())
                    .deadline(debate.getCreateTime())
                    .build();

        }
    }


}
