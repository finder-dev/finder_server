package com.cmc.finder.api.user.dto;

import com.cmc.finder.domain.community.entity.Community;
import com.cmc.finder.domain.debate.entity.Debate;
import com.cmc.finder.domain.model.MBTI;
import com.cmc.finder.domain.qna.question.entity.Question;
import com.cmc.finder.global.util.DateTimeUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class GetUserActivityRes {

    private Long communityId;

    private String communityTitle;

    private String communityContent;

    private MBTI communityMBTI;

    private String userNickname;

    private MBTI userMBTI;

    private Boolean isQuestion;

    private String createTime;

    @Builder
    public GetUserActivityRes(Long communityId, String title, String content, MBTI mbti,
                               String userNickname, MBTI userMBTI, Integer likeCount,
                               Integer answerCount, Boolean isQuestion, String createTime) {

        this.communityId = communityId;
        this.communityTitle = title;
        this.communityContent = content;
        this.communityMBTI = mbti;
        this.userNickname = userNickname;
        this.userMBTI = userMBTI;
        this.isQuestion = isQuestion;
        this.createTime = createTime;

    }

    public static GetUserActivityRes of(Community community) {

        GetUserActivityRes response = GetUserActivityRes.builder()
                .communityId(community.getCommunityId())
                .title(community.getTitle())
                .content(community.getContent())
                .mbti(community.getMbti())
                .userNickname(community.getUser().getNickname())
                .userMBTI(community.getUser().getMbti())
                .isQuestion(community.getIsQuestion())
                .createTime(DateTimeUtils.convertToLocalDatetimeToTime(community.getCreateTime()))
                .build();

        return response;

    }



}
