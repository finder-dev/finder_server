package com.cmc.finder.api.community.dto;

import com.cmc.finder.domain.community.entity.Community;
import com.cmc.finder.domain.community.entity.CommunityAnswer;
import com.cmc.finder.domain.community.entity.CommunityImage;
import com.cmc.finder.domain.model.MBTI;
import com.cmc.finder.global.util.DateTimeUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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

    private Boolean saveUser;

    private Long userId;

    private MBTI userMBTI;

    private String userNickname;

    private Integer answerCount;

    private Boolean isQuestion;

    private String createTime;

    private List<AnswerHistDto> answerHistDtos = new ArrayList<>();

    @Builder
    public CommunityDetailDto(Long communityId, String communityTitle, String communityContent, MBTI communityMBTI,
                              List<CommunityImageDto> communityImgDtos, Integer likeCount, Boolean likeUser,
                              Long userId, Boolean saveUser, MBTI userMBTI, String userNickname, String createTime,
                              Integer answerCount, Boolean isQuestion, List<AnswerHistDto> answerHistDtos) {

        this.communityId = communityId;
        this.communityTitle = communityTitle;
        this.communityContent = communityContent;
        this.communityMBTI = communityMBTI;
        this.communityImgDtos = communityImgDtos;
        this.likeCount = likeCount;
        this.likeUser = likeUser;
        this.saveUser = saveUser;
        this.userId = userId;
        this.userMBTI = userMBTI;
        this.userNickname = userNickname;
        this.answerCount = answerCount;
        this.isQuestion = isQuestion;
        this.createTime = createTime;
        this.answerHistDtos = answerHistDtos;
    }

    public static CommunityDetailDto of(Community community, List<CommunityAnswer> answers, Boolean likeUser, Boolean saveUser) {

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
                .saveUser(saveUser)
                .userId(community.getUser().getUserId())
                .userMBTI(community.getUser().getMbti())
                .userNickname(community.getUser().getNickname())
                .answerCount(community.getCommunityAnswers().size())
                .isQuestion(community.getIsQuestion())
                .createTime(DateTimeUtils.convertToLocalDatetimeToTime(community.getCreateTime()))
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

        private Long userId;

        private MBTI userMBTI;

        private String userNickname;

        private String createTime;

        private List<ReplyHistDto> replyHistDtos = new ArrayList<>();

        @Builder
        public AnswerHistDto(Long answerId, String answerContent, MBTI userMBTI, String userNickname,
                             Long userId, List<ReplyHistDto> replyHistDtos, String createTime) {
            this.answerId = answerId;
            this.answerContent = answerContent;
            this.userId = userId;
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
                    .userId(answer.getUser().getUserId())
                    .userMBTI(answer.getUser().getMbti())
                    .userNickname(answer.getUser().getNickname())
                    .replyHistDtos(replies)
                    .createTime(DateTimeUtils.convertToLocalDatetimeToTime(answer.getCreateTime()))
                    .build();
        }

        @Getter
        @Setter
        private static class ReplyHistDto {

            private Long replyId;

            private String replyContent;

            private Long userId;

            private MBTI userMBTI;

            private String userNickname;

            private String createTime;

            @Builder
            public ReplyHistDto(Long replyId, String replyContent, MBTI userMBTI,
                                Long userId, String userNickname, String createTime) {
                this.replyId = replyId;
                this.replyContent = replyContent;
                this.userId = userId;
                this.userMBTI = userMBTI;
                this.userNickname = userNickname;
                this.createTime = createTime;
            }

            public static ReplyHistDto of(CommunityAnswer answer) {

                return ReplyHistDto.builder()
                        .replyId(answer.getCommunityAnswerId())
                        .replyContent(answer.getContent())
                        .userId(answer.getUser().getUserId())
                        .userMBTI(answer.getUser().getMbti())
                        .userNickname(answer.getUser().getNickname())
                        .createTime(DateTimeUtils.convertToLocalDatetimeToTime(answer.getCreateTime()))
                        .build();
            }

        }


    }


}
