package com.cmc.finder.api.qna.qustion.dto;

import com.cmc.finder.domain.model.MBTI;
import com.cmc.finder.domain.qna.question.entity.FavoriteQuestion;
import com.cmc.finder.domain.qna.question.entity.Question;
import com.cmc.finder.domain.user.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class FavoriteQuestionResponse {

    private Long questionId;

    private String title;

    private String content;

    private String userNickname;

    private MBTI userMBTI;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    public static FavoriteQuestionResponse of(User user, FavoriteQuestion favoriteQuestion) {

        FavoriteQuestionResponse response = FavoriteQuestionResponse.builder()
                .questionId(favoriteQuestion.getQuestion().getQuestionId())
                .title(favoriteQuestion.getQuestion().getTitle())
                .content(favoriteQuestion.getQuestion().getContent())
                .userNickname(user.getNickname())
                .userMBTI(user.getMbti())
                .createTime(favoriteQuestion.getQuestion().getCreateTime())
                .build();

        return response;

    }


}
