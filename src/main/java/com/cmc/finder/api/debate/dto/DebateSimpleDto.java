package com.cmc.finder.api.debate.dto;

import com.cmc.finder.domain.debate.entity.Debate;
import com.cmc.finder.domain.qna.question.constant.OrderBy;
import com.cmc.finder.global.validator.Enum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


public class DebateSimpleDto {

    @Getter @Setter
    public static class Request {

        @Enum(enumClass = OrderBy.class, message = "잘못된 Enum 값 입니다.", ignoreCase = true)
        private String orderBy;

    }

    @Getter @Setter
    public static class Response {

        private Long debateId;

        private String title;

        private Integer joinCount;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createTime;

        @Builder
        public Response(Long debateId, String title, Integer joinCount, LocalDateTime createTime) {
            this.debateId = debateId;
            this.title = title;
            this.joinCount = joinCount;
            this.createTime = createTime;
        }

        public static Response of(Debate debate) {

            return Response.builder()
                    .debateId(debate.getDebateId())
                    .title(debate.getTitle())
                    .joinCount(debate.getDebaters().size())
                    .createTime(debate.getCreateTime())
                    .build();

        }
    }


}
