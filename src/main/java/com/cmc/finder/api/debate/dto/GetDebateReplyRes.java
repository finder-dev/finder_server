package com.cmc.finder.api.debate.dto;

import com.cmc.finder.domain.debate.entity.DebateAnswerReply;
import com.cmc.finder.domain.model.MBTI;
import com.cmc.finder.domain.qna.answer.entity.AnswerReply;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class GetDebateReplyRes {

    private Long debateReplyId;

    private String content;

    private String userNickname;

    private MBTI userMBTI;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    public static GetDebateReplyRes of(DebateAnswerReply debateAnswerReply) {

        GetDebateReplyRes response = GetDebateReplyRes.builder()
                .debateReplyId(debateAnswerReply.getDebateReplyId())
                .content(debateAnswerReply.getContent())
                .userNickname(debateAnswerReply.getUser().getNickname())
                .userMBTI(debateAnswerReply.getUser().getMbti())
                .createTime(debateAnswerReply.getCreateTime())
                .build();

        return response;

    }

}
