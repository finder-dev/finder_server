package com.cmc.finder.api.community.dto;

import com.cmc.finder.api.debate.dto.DebateCreateDto;
import com.cmc.finder.domain.debate.entity.Debate;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CommunityHotDto {

    private Long communityId;

    private String title;

    public static CommunityHotDto of(Long communityId, String title) {
        return CommunityHotDto.builder()
                .communityId(communityId)
                .title(title)
                .build();

    }

}
