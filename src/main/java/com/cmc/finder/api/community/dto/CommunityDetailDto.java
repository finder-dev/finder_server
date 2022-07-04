package com.cmc.finder.api.community.dto;

import com.cmc.finder.domain.community.entity.Community;
import com.cmc.finder.domain.community.entity.CommunityAnswer;
import com.cmc.finder.domain.community.entity.CommunityImage;
import com.cmc.finder.domain.model.MBTI;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class CommunityDetailDto {

    private Long communityId;

    private String communityTitle;

    private String communityContent;

    private MBTI communityMBTI;

    private List<CommunityImageDto> communityImgDtos = new ArrayList<>();

    private Integer likeCount;

    private Boolean likeUser;

    private MBTI userMBTI;

    private String userNickname;

    private Integer answerCount;

    private Boolean isQuestion;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    private List<AnswerHistDto> answerHistDtos = new ArrayList<>();

    @Builder
    public CommunityDetailDto(Long communityId, String communityTitle, String communityContent, MBTI communityMBTI,
                              List<CommunityImageDto> communityImgDtos, Integer likeCount, Boolean likeUser, MBTI userMBTI, String userNickname, LocalDateTime createTime,
                              Integer answerCount, Boolean isQuestion, List<AnswerHistDto> answerHistDtos) {

        this.communityId = communityId;
        this.communityTitle = communityTitle;
        this.communityContent = communityContent;
        this.communityMBTI = communityMBTI;
        this.communityImgDtos = communityImgDtos;
        this.likeCount = likeCount;
        this.likeUser = likeUser;
        this.userMBTI = userMBTI;
        this.userNickname = userNickname;
        this.answerCount = answerCount;
        this.isQuestion = isQuestion;
        this.createTime = createTime;
        this.answerHistDtos = answerHistDtos;
    }

    public static CommunityDetailDto of(Community community, List<CommunityAnswer> answers, Boolean likeUser) {

        List<CommunityImageDto> communityImageDtos = community.getCommunityImages().stream().map(communityImage ->
                CommunityImageDto.of(communityImage)
        ).collect(Collectors.toList());

        List<CommunityDetailDto.AnswerHistDto> answerHistDtos = answers.stream().map(answer ->
                AnswerHistDto.of(answer)
        ).collect(Collectors.toList());

        return CommunityDetailDto.builder()
                .communityId(community.getCommunityId())
                .communityTitle(community.getTitle())
                .communityContent(community.getContent())
                .communityMBTI(community.getMbti())
                .communityImgDtos(communityImageDtos)
                .likeCount(community.getLikeList().size())
                .likeUser(likeUser)
                .userMBTI(community.getUser().getMbti())
                .userNickname(community.getUser().getNickname())
                .answerCount(answers.size())
                .isQuestion(community.getIsQuestion())
                .createTime(community.getCreateTime())
                .answerHistDtos(answerHistDtos)
                .build();
    }

    @Getter
    @Setter
    public static class CommunityImageDto {

        private Long communityImageId;

        private String communityImageUrl;

        @Builder
        public CommunityImageDto(Long communityImageId, String communityImageUrl) {
            this.communityImageId = communityImageId;
            this.communityImageUrl = communityImageUrl;
        }

        public static CommunityImageDto of(CommunityImage communityImage) {

            return CommunityImageDto.builder()
                    .communityImageId(communityImage.getCommunityImageId())
                    .communityImageUrl(communityImage.getImageUrl())
                    .build();
        }
    }

    @Getter
    @Setter
    public static class AnswerHistDto {

        private Long answerId;

        private String answerContent;

        private MBTI userMBTI;

        private String userNickname;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createTime;

        private List<ReplyHistDto> replyHistDtos = new ArrayList<>();

        @Builder
        public AnswerHistDto(Long answerId, String answerContent, MBTI userMBTI, String userNickname,List<ReplyHistDto> replyHistDtos, LocalDateTime createTime) {
            this.answerId = answerId;
            this.answerContent = answerContent;
            this.userMBTI = userMBTI;
            this.userNickname = userNickname;
            this.replyHistDtos = replyHistDtos;
            this.createTime = createTime;
        }

        public static AnswerHistDto of(CommunityAnswer answer) {

            Collections.reverse(answer.getReplies());
            List<ReplyHistDto> replies = answer.getReplies().stream().map(reply ->
                    ReplyHistDto.of(reply)
            ).collect(Collectors.toList());

            return AnswerHistDto.builder()
                    .answerId(answer.getCommunityAnswerId())
                    .answerContent(answer.getContent())
                    .userMBTI(answer.getUser().getMbti())
                    .userNickname(answer.getUser().getNickname())
                    .replyHistDtos(replies)
                    .createTime(answer.getCreateTime())
                    .build();
        }

        @Getter
        @Setter
        private static class ReplyHistDto {

            private Long replyId;

            private String replyContent;

            private MBTI userMBTI;

            private String userNickname;

            @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            private LocalDateTime createTime;

            @Builder
            public ReplyHistDto(Long replyId, String replyContent, MBTI userMBTI, String userNickname, LocalDateTime createTime) {
                this.replyId = replyId;
                this.replyContent = replyContent;
                this.userMBTI = userMBTI;
                this.userNickname = userNickname;
                this.createTime = createTime;
            }

            public static ReplyHistDto of(CommunityAnswer answer) {

                return ReplyHistDto.builder()
                        .replyId(answer.getCommunityAnswerId())
                        .replyContent(answer.getContent())
                        .userMBTI(answer.getUser().getMbti())
                        .userNickname(answer.getUser().getNickname())
                        .createTime(answer.getCreateTime())
                        .build();
            }

        }


    }



}
