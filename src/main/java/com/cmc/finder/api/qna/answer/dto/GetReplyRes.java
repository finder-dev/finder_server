package com.cmc.finder.api.qna.answer.dto;

import com.cmc.finder.api.qna.qustion.dto.FavoriteQuestionRes;
import com.cmc.finder.domain.model.MBTI;
import com.cmc.finder.domain.qna.answer.entity.Reply;
import com.cmc.finder.domain.qna.question.entity.FavoriteQuestion;
import com.cmc.finder.domain.user.entity.User;
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

    public static GetReplyRes of(Reply reply) {

        GetReplyRes response = GetReplyRes.builder()
                .replyId(reply.getReplyId())
                .content(reply.getContent())
                .userNickname(reply.getUser().getNickname())
                .userMBTI(reply.getUser().getMbti())
                .build();

        return response;

    }

}
