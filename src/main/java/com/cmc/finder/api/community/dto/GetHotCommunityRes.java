package com.cmc.finder.api.community.dto;

import com.cmc.finder.domain.community.entity.Community;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


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
