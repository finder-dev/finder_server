package com.cmc.finder.api.debate.dto;

import com.cmc.finder.domain.debate.entity.DebateAnswer;
import com.cmc.finder.domain.qna.answer.entity.Answer;
import com.cmc.finder.domain.debate.entity.Debate;
import com.cmc.finder.domain.model.MBTI;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    private String writerNickname;

    private MBTI writerMBTI;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    private List<AnswerHistDto> answerHistDtos = new ArrayList<>();

    @Builder
    public DebateDetailDto(Long debateId, String debateTitle, String optionA, Long optionACount, String optionB, Long optionBCount,
                           Integer answerCount, String writerNickname, MBTI writerMBTI, LocalDateTime createTime, List<AnswerHistDto> answerHistDtos) {
        this.debateId = debateId;
        this.debateTitle = debateTitle;
        this.optionA = optionA;
        this.optionACount = optionACount;
        this.optionB = optionB;
        this.optionBCount = optionBCount;
        this.answerCount = answerCount;
        this.writerNickname = writerNickname;
        this.writerMBTI = writerMBTI;
        this.createTime = createTime;
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

        private MBTI userMBTI;

        private String userNickname;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createTime;

        @Builder
        public AnswerHistDto(Long debateAnswerId, String debateAnswerContent, MBTI userMBTI, String userNickname, LocalDateTime createTime) {
            this.debateAnswerId = debateAnswerId;
            this.debateAnswerContent = debateAnswerContent;
            this.userMBTI = userMBTI;
            this.userNickname = userNickname;
            this.createTime = createTime;
        }

        public static DebateDetailDto.AnswerHistDto of(DebateAnswer answer) {

            return AnswerHistDto.builder()
                    .debateAnswerId(answer.getDebateAnswerId())
                    .debateAnswerContent(answer.getContent())
                    .userMBTI(answer.getUser().getMbti())
                    .userNickname(answer.getUser().getNickname())
                    .createTime(answer.getCreateTime())
                    .build();
        }
    }

}
