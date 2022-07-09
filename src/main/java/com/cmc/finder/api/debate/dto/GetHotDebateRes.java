package com.cmc.finder.api.debate.dto;

import com.cmc.finder.domain.debate.entity.Debate;
import com.cmc.finder.domain.debate.entity.Debater;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class GetHotDebateRes {

    private Long debateId;

    private String title;

    private String optionA;

    private Long optionACount;

    private String optionB;

    private Long optionBCount;

    private Boolean join;

    private String joinOption;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    //TODO 수정
    public static GetHotDebateRes of(Debate debate, Long optionACount, Long optionBCount, Boolean alreadyJoin) {

        GetHotDebateRes res = GetHotDebateRes.builder()
                .debateId(debate.getDebateId())
                .title(debate.getTitle())
                .optionA(debate.getOptionA())
                .optionACount(optionACount)
                .optionB(debate.getOptionB())
                .optionBCount(optionBCount)
                .createTime(debate.getCreateTime())
                .join(alreadyJoin)
                .build();


        return res;

    }
}
