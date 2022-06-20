package com.cmc.finder.api.qna.qustion.dto;

import com.cmc.finder.domain.model.MBTI;
import com.cmc.finder.domain.question.entity.Question;
import com.cmc.finder.domain.user.entity.User;
import com.cmc.finder.global.validator.Enum;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class QuestionUpdateDto {

    @Getter
    @Setter
    public static class Request {

        @NotBlank(message = "글 제목은 필수값 입니다.")
        private String title;

        @NotBlank(message = "글 내용은 필수값 입니다.")
        private String content;

        @NotBlank(message = "MBTI는 필수값 입니다.")
        @Enum(enumClass = MBTI.class, message = "잘못된 Enum 값 입니다.")
        private String mbti;

        private List<String> originImgNames = new ArrayList<>();

        private List<MultipartFile> questionUpdateImgs = new ArrayList<>();

        public Question toEntity() {
            return Question.builder()

                    .title(title)
                    .content(content)
                    .mbti(MBTI.from(mbti))
                    .build();
        }



    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {

        //TODO 응답값 고민

        private String title;

        private String content;

        private MBTI mbti;

        private List<String> imgUrls = new ArrayList<>();

        public static Response of(Question question) {

            List<String> imgUrls = question.getQuestionImages().stream().map(questionImage ->
                    questionImage.getImageUrl()
            ).collect(Collectors.toList());

            return Response.builder()
                    .title(question.getTitle())
                    .content(question.getContent())
                    .mbti(question.getMbti())
                    .imgUrls(imgUrls)
                    .build();

        }
    }
}
