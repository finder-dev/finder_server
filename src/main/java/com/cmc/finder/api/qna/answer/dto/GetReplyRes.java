package com.cmc.finder.api.qna.answer.dto;

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
public class GetReplyRes {

    private Long replyId;

    private String content;

    private String userNickname;

    private MBTI userMBTI;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    public static GetReplyRes of(AnswerReply answerReply) {

        GetReplyRes response = GetReplyRes.builder()
                .replyId(answerReply.getReplyId())
                .content(answerReply.getContent())
                .userNickname(answerReply.getUser().getNickname())
                .userMBTI(answerReply.getUser().getMbti())
                .createTime(answerReply.getCreateTime())
                .build();

        return response;

    }

}
