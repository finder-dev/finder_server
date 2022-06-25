package com.cmc.finder.api.qna.answer.dto;

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

    }

    @Getter @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {

        private Long questionId;

        public static AnswerCreateDto.Response of(Question question) {

            return Response.builder()
                    .questionId(question.getQuestionId())
                    .build();

        }
    }
}
