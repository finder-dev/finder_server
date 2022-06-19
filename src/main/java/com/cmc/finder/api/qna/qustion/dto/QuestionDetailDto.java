package com.cmc.finder.api.qna.qustion.dto;

import com.cmc.finder.domain.answer.entity.Answer;
import com.cmc.finder.domain.model.MBTI;
import com.cmc.finder.domain.question.entity.Question;
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
public class QuestionDetailDto {

    private Long questionId;

    private String questionTitle;

    private String questionContent;

    private MBTI questionMBTI;

    private List<String> questionImgUrls = new ArrayList<>();

    private Integer curiousCount;

    private MBTI userMBTI;

    private String userNickname;

    private Long viewCount;

    private Boolean curiousUser;

    private Boolean favoriteUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    private List<AnswerHistDto> answerHistDtos = new ArrayList<>();

    @Builder
    public QuestionDetailDto(Long questionId, String questionTitle, String questionContent, MBTI questionMBTI,
                             List<String> questionImgUrls, Integer curiousCount, MBTI userMBTI, String userNickname, LocalDateTime createTime,
                             Long viewCount, Boolean favoriteUser, Boolean curiousUser, List<AnswerHistDto> answerHistDtos) {

        this.questionId = questionId;
        this.questionTitle = questionTitle;
        this.questionContent = questionContent;
        this.questionMBTI = questionMBTI;
        this.questionImgUrls = questionImgUrls;
        this.curiousCount = curiousCount;
        this.userMBTI = userMBTI;
        this.userNickname = userNickname;
        this.viewCount = viewCount;
        this.favoriteUser = favoriteUser;
        this.curiousUser = curiousUser;
        this.createTime = createTime;
        this.answerHistDtos = answerHistDtos;
    }

    public static QuestionDetailDto of(Question question, List<Answer> answers, Long viewCount, Boolean favoriteUser, Boolean curiousUser) {

        List<String> questionImgUrls = question.getQuestionImages().stream().map(questionImage ->
                questionImage.getImageUrl()
        ).collect(Collectors.toList());

        List<QuestionDetailDto.AnswerHistDto> answerHistDtos = answers.stream().map(answer ->
                AnswerHistDto.of(answer)
        ).collect(Collectors.toList());

        return QuestionDetailDto.builder()
                .questionId(question.getQuestionId())
                .questionTitle(question.getTitle())
                .questionContent(question.getContent())
                .questionMBTI(question.getMbti())
                .questionImgUrls(questionImgUrls)
                .curiousCount(question.getCuriousList().size())
                .userMBTI(question.getUser().getMbti())
                .userNickname(question.getUser().getNickname())
                .viewCount(viewCount)
                .favoriteUser(favoriteUser)
                .curiousUser(curiousUser)
                .createTime(question.getCreateTime())
                .answerHistDtos(answerHistDtos)
                .build();
    }

    @Getter
    @Setter
    public static class AnswerHistDto {

        private Long answerId;

        private String answerTitle;

        private String answerContent;

        private List<String> answerImgUrls = new ArrayList<>();

        private MBTI userMBTI;

        private String userNickname;

        private Integer helpfulCount;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createTime;

        @Builder
        public AnswerHistDto(Long answerId, String answerTitle, String answerContent, List<String> answerImgUrls,
                             MBTI userMBTI, String userNickname, Integer helpfulCount, LocalDateTime createTime) {
            this.answerId = answerId;
            this.answerTitle = answerTitle;
            this.answerContent = answerContent;
            this.answerImgUrls = answerImgUrls;
            this.userMBTI = userMBTI;
            this.userNickname = userNickname;
            this.helpfulCount = helpfulCount;
            this.createTime = createTime;
        }

        public static QuestionDetailDto.AnswerHistDto of(Answer answer) {

            List<String> answerImgUrls = answer.getAnswerImages().stream().map(answerImage ->
                    answerImage.getImageUrl()
            ).collect(Collectors.toList());

            return AnswerHistDto.builder()
                    .answerId(answer.getAnswerId())
                    .answerTitle(answer.getTitle())
                    .answerContent(answer.getContent())
                    .answerImgUrls(answerImgUrls)
                    .userMBTI(answer.getUser().getMbti())
                    .userNickname(answer.getUser().getNickname())
                    .helpfulCount(answer.getHelpfuls().size())
                    .createTime(answer.getCreateTime())
                    .build();
        }
    }

}
