package com.cmc.finder.api.debate.dto;

import com.cmc.finder.domain.debate.entity.DebateAnswer;
import com.cmc.finder.domain.debate.entity.Debate;
import com.cmc.finder.domain.model.MBTI;
import com.cmc.finder.global.util.DateTimeUtils;
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
public class DebateDetailDto {

    private Long debateId;

    private String debateTitle;

    private String optionA;

    private Long optionACount;

    private String optionB;

    private Long optionBCount;

    private Integer answerCount;

    private Long writerId;

    private String writerNickname;

    private MBTI writerMBTI;

    private String deadline;

    private List<AnswerHistDto> answerHistDtos = new ArrayList<>();

    @Builder
    public DebateDetailDto(Long debateId, String debateTitle, String optionA, Long optionACount, String optionB, Long optionBCount,
                           Integer answerCount, Long writerId, String writerNickname, MBTI writerMBTI, LocalDateTime createTime, List<AnswerHistDto> answerHistDtos) {
        this.debateId = debateId;
        this.debateTitle = debateTitle;
        this.optionA = optionA;
        this.optionACount = optionACount;
        this.optionB = optionB;
        this.optionBCount = optionBCount;
        this.answerCount = answerCount;
        this.writerId = writerId;
        this.writerNickname = writerNickname;
        this.writerMBTI = writerMBTI;
        this.deadline = DateTimeUtils.convertToLocalDateTimeToDeadline(createTime);
        this.answerHistDtos = answerHistDtos;
    }

    public static DebateDetailDto of(Debate debate, List<DebateAnswer> answers, Long optionACount, Long optionBCount) {

        List<DebateDetailDto.AnswerHistDto> answerHistDtos = answers.stream().map(answer ->
                AnswerHistDto.of(answer)
        ).collect(Collectors.toList());

        return DebateDetailDto.builder()
                .debateId(debate.getDebateId())
                .debateTitle(debate.getTitle())
                .optionA(debate.getOptionA())
                .optionACount(optionACount)
                .optionB(debate.getOptionB())
                .optionBCount(optionBCount)
                .answerCount(answers.size())
                .writerId(debate.getWriter().getUserId())
                .writerNickname(debate.getWriter().getNickname())
                .writerMBTI(debate.getWriter().getMbti())
                .createTime(debate.getCreateTime())
                .answerHistDtos(answerHistDtos)
                .build();
    }

    @Getter
    @Setter
    public static class AnswerHistDto {

        private Long debateAnswerId;

        private String debateAnswerContent;

        private Long userId;

        private MBTI userMBTI;

        private String userNickname;

        private String createTime;

        private List<ReplyHistDto> replyHistDtos = new ArrayList<>();


        @Builder
        public AnswerHistDto(Long debateAnswerId, String debateAnswerContent, Long userId, MBTI userMBTI, String userNickname,
                             List<ReplyHistDto> replyHistDtos, LocalDateTime createTime) {
            this.debateAnswerId = debateAnswerId;
            this.debateAnswerContent = debateAnswerContent;
            this.userMBTI = userMBTI;
            this.userId = userId;
            this.userNickname = userNickname;
            this.replyHistDtos = replyHistDtos;
            this.createTime = DateTimeUtils.convertToLocalDatetimeToTime(createTime);
        }

        public static DebateDetailDto.AnswerHistDto of(DebateAnswer answer) {

            Collections.reverse(answer.getReplies());
            List<ReplyHistDto> replies = answer.getReplies().stream().map(reply ->
                    ReplyHistDto.of(reply)
            ).collect(Collectors.toList());

            return AnswerHistDto.builder()
                    .debateAnswerId(answer.getDebateAnswerId())
                    .debateAnswerContent(answer.getContent())
                    .userId(answer.getUser().getUserId())
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

            public static ReplyHistDto of(DebateAnswer answer) {

                return ReplyHistDto.builder()
                        .replyId(answer.getDebateAnswerId())
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
