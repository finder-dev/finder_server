package com.cmc.finder.api.qna.qustion.dto;

import com.cmc.finder.domain.jwt.dto.TokenDto;
import com.cmc.finder.domain.model.MBTI;
import com.cmc.finder.domain.question.entity.Question;
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

        @NotNull(message = "MBTI는 필수값 입니다.")
        private MBTI mbti;

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
