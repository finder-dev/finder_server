package com.cmc.finder.api.qna.qustion.dto;

import com.cmc.finder.domain.model.MBTI;
import com.cmc.finder.domain.question.entity.Question;
import com.cmc.finder.global.validator.Enum;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


public class QuestionCreateDto {

    @Getter @Setter
    public static class Request {

        @NotBlank(message = "글 제목은 필수값 입니다.")
        private String title;

        @NotBlank(message = "글 내용은 필수값 입니다.")
        private String content;

        @NotBlank(message = "MBTI는 필수값 입니다.")
        @Enum(enumClass = MBTI.class, message ="잘못된 Enum 값 입니다.")
        private String mbti;

        private List<MultipartFile> questionImgs = new ArrayList<>();

    }

    @Getter @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {

        private String title;

        private String nickname;

        public static Response of(Question question) {
            return QuestionCreateDto.Response.builder()
                    .title(question.getTitle())
                    .nickname(question.getUser().getNickname())
                    .build();

        }
    }
}
