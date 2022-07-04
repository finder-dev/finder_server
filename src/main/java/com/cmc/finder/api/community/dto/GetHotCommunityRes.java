package com.cmc.finder.api.community.dto;

import com.cmc.finder.api.qna.qustion.dto.GetHotQuestionRes;
import com.cmc.finder.domain.community.entity.Community;
import com.cmc.finder.domain.debate.entity.Debate;
import com.cmc.finder.domain.debate.entity.Debater;
import com.cmc.finder.domain.qna.question.entity.Question;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class GetHotCommunityRes {

    private Long communityId;

    private String title;

    public static GetHotCommunityRes of(Community community) {
        return GetHotCommunityRes.builder()
                .communityId(community.getCommunityId())
                .title(community.getTitle())
                .build();

    }
}
