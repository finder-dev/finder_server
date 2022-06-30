package com.cmc.finder.api.qna.answer.dto;

import com.cmc.finder.domain.model.MBTI;
import com.cmc.finder.domain.qna.answer.entity.Answer;
import com.cmc.finder.domain.qna.question.entity.Question;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class AnswerCreateDto {

    @Getter @Setter
    public static class Request {

        @NotBlank(message = "글 제목은 필수값 입니다.")
        private String title;

        @NotBlank(message = "글 내용은 필수값 입니다.")
        private String content;

        @Size(max = 10, message = "최대 10개까지 사진을 추가하실 수 있습니다.")
        private List<MultipartFile> answerImgs = new ArrayList<>();

        public Answer toEntity() {

            return Answer.builder()
                    .title(title)
                    .content(content)
                    .build();
        }



    }

    @Getter @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {

        private Long answerId;

        public static AnswerCreateDto.Response of(Answer answer) {

            return Response.builder()
                    .answerId(answer.getAnswerId())
                    .build();

        }
    }
}
