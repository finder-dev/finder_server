package com.cmc.finder.api.user.dto;

import com.cmc.finder.domain.debate.entity.Debate;
import com.cmc.finder.domain.qna.question.entity.Question;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter @Setter
public class UserActivityResponse {

    private Long id;

    //TODO 타입 추가 -> 커뮤, qna, 토론

    private String title;

    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    public static UserActivityResponse of(Object obj) {

        UserActivityResponse response;

        if (obj instanceof Question) {

            Question question = (Question) obj;
            response = UserActivityResponse.builder()
                    .id(question.getQuestionId())
                    .content(question.getContent())
                    .title(question.getTitle())
                    .createTime(question.getCreateTime())
                    .build();
        }
        //TODO 삭제
        else {

            Debate debate = (Debate) obj;
            response = UserActivityResponse.builder()
                    .id(debate.getDebateId())
                    .title(debate.getTitle())
                    .content(null)
                    .createTime(debate.getCreateTime())
                    .build();
        }

        return response;

    }



}
