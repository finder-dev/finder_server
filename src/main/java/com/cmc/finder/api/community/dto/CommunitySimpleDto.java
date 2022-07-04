package com.cmc.finder.api.community.dto;

import com.cmc.finder.domain.community.entity.Community;
import com.cmc.finder.domain.model.MBTI;
import com.cmc.finder.domain.model.OrderBy;
import com.cmc.finder.global.validator.Enum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


public class CommunitySimpleDto {

    @Getter
    @Setter
    public static class Request {

        @Enum(enumClass = MBTI.class, message = "잘못된 Enum 값 입니다.", ignoreCase = true)
        private String mbti;

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

        private String imageUrl;

        private String userNickname;

        private MBTI userMBTI;

        private Integer likeCount;

        private Integer answerCount;

        private Boolean isQuestion;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createTime;

        @Builder
        public Response(Long communityId, String title, String content, MBTI mbti, String imageUrl,
                        String userNickname, MBTI userMBTI, Integer likeCount,
                        Integer answerCount, Boolean isQuestion, LocalDateTime createTime) {

            this.communityId = communityId;
            this.communityTitle = title;
            this.communityContent = content;
            this.communityMBTI = mbti;
            this.imageUrl = imageUrl;
            this.userNickname = userNickname;
            this.userMBTI = userMBTI;
            this.likeCount = likeCount;
            this.answerCount = answerCount;
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
                    .likeCount(community.getLikeList().size())
                    .answerCount(community.getCommunityAnswers().size())
                    .isQuestion(community.getIsQuestion())
                    .createTime(community.getCreateTime())
                    .build();

            if (community.getCommunityImages().size() != 0) {
                response.imageUrl = community.getCommunityImages().get(0).getImageUrl();
            }

            return response;

        }
    }


}
