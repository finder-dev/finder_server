package com.cmc.finder.api.user.dto;

import com.cmc.finder.domain.community.entity.Community;
import com.cmc.finder.domain.model.MBTI;
import com.cmc.finder.global.util.DateTimeUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetSaveCommunityRes {

    private Long communityId;

    private String communityTitle;

    private String communityContent;

    private MBTI communityMBTI;

    private String userNickname;

    private MBTI userMBTI;

    private Boolean isQuestion;

    private String createTime;

    @Builder
    public GetSaveCommunityRes(Long communityId, String title, String content, MBTI mbti,
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

    public static GetSaveCommunityRes of(Community community) {

        GetSaveCommunityRes response = GetSaveCommunityRes.builder()
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


