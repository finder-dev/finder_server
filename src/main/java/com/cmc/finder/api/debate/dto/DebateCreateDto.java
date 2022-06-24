package com.cmc.finder.api.debate.dto;

import com.cmc.finder.domain.model.MBTI;
import com.cmc.finder.domain.question.entity.Question;
import com.cmc.finder.global.validator.Enum;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


public class DebateCreateDto {

    @Getter @Setter
    public static class Request {

        @NotBlank(message = "글 제목은 필수값 입니다.")
        private String title;

        @NotBlank(message = "토론 선택지는 필수값 입니다.")
        private String optionA;

        @NotBlank(message = "토론 선택지는 필수값 입니다.")
        private String optionB;

    }

    @Getter @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {

        private String title;

        private String nickname;

        public static Response of(Question question) {
            return DebateCreateDto.Response.builder()
                    .title(question.getTitle())
                    .nickname(question.getUser().getNickname())
                    .build();

        }
    }
}
