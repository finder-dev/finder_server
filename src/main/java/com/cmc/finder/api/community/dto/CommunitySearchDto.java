package com.cmc.finder.api.community.dto;

import com.cmc.finder.domain.community.entity.Community;
import com.cmc.finder.domain.model.MBTI;
import com.cmc.finder.domain.model.OrderBy;
import com.cmc.finder.global.util.DateTimeUtils;
import com.cmc.finder.global.validator.Enum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


public class CommunitySearchDto {

    @Getter
    @Setter
    public static class Request {

        @NotBlank(message = "검색어는 필수값입니다.")
        private String searchQuery;

        @Enum(enumClass = OrderBy.class, message = "잘못된 Enum 값 입니다.", ignoreCase = true)
        private String orderBy;

    }

    @Getter
    @Setter
    public static class Response {


        private Long communityId;

        private String communityTitle;

        private String communityContent;

        private MBTI communityMBTI;

        private String userNickname;

        private MBTI userMBTI;

        private Boolean isQuestion;

        private String createTime;

        @Builder
        public Response(Long communityId, String title, String content, MBTI mbti,
                        String userNickname, MBTI userMBTI, Boolean isQuestion, String createTime) {

            this.communityId = communityId;
            this.communityTitle = title;
            this.communityContent = content;
            this.communityMBTI = mbti;
            this.userNickname = userNickname;
            this.userMBTI = userMBTI;
            this.isQuestion = isQuestion;
            this.createTime = createTime;

        }

        public static Response of(Community community) {

            Response response = Response.builder()
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


}
