package com.cmc.finder.api.qna.qustion.dto;

import com.cmc.finder.domain.model.MBTI;
import com.cmc.finder.domain.qna.question.constant.OrderBy;
import com.cmc.finder.domain.qna.question.entity.Question;
import com.cmc.finder.global.validator.Enum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;


public class QuestionSimpleDto {

    @Getter
    @Setter
    public static class Request {

        @NotBlank(message = "MBTI는 필수값 입니다.")
        @Enum(enumClass = MBTI.class, message = "잘못된 Enum 값 입니다.")
        private String mbti;

        @Enum(enumClass = OrderBy.class, message = "잘못된 Enum 값 입니다.", ignoreCase = true)
        private String orderBy;

    }

    @Getter
    @Setter
    public static class Response {
        private Long questionId;

        private String title;

        private String content;

        private String imageUrl;

        private String userNickname;

        private MBTI userMBTI;

        private Integer curiousCount;

        private Integer answerCount;

        private Integer viewCount;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createTime;

        @Builder
        public Response(Long questionId, String title, String content, String imageUrl,
                        String userNickname, MBTI userMBTI, Integer curiousCount,
                        Integer answerCount, Integer viewCount, LocalDateTime createTime) {
            this.questionId = questionId;
            this.title = title;
            this.content = content;
            this.imageUrl = imageUrl;
            this.userNickname = userNickname;
            this.userMBTI = userMBTI;
            this.curiousCount = curiousCount;
            this.answerCount = answerCount;
            this.viewCount = viewCount;
            this.createTime = createTime;
        }

        public static Response of(Question question) {

            Response response = Response.builder()
                    .questionId(question.getQuestionId())
                    .title(question.getTitle())
                    .content(question.getContent())
                    .userNickname(question.getUser().getNickname())
                    .userMBTI(question.getUser().getMbti())
                    .curiousCount(question.getCuriousList().size())
                    .answerCount(question.getAnswers().size())
                    .viewCount(question.getViewCounts().size())
                    .createTime(question.getCreateTime())
                    .build();

            if (question.getQuestionImages().size() != 0) {
                response.imageUrl = question.getQuestionImages().get(0).getImageUrl();
            }

            return response;

        }
    }


}
