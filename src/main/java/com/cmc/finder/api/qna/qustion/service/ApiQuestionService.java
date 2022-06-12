package com.cmc.finder.api.qna.qustion.service;

import com.cmc.finder.api.qna.qustion.dto.QuestionCreateDto;
import com.cmc.finder.domain.model.Email;
import com.cmc.finder.domain.question.entity.Question;
import com.cmc.finder.domain.question.entity.QuestionImage;
import com.cmc.finder.domain.question.service.QuestionImageService;
import com.cmc.finder.domain.question.service.QuestionService;
import com.cmc.finder.domain.user.entity.User;
import com.cmc.finder.domain.user.service.UserService;
import com.cmc.finder.infra.file.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ApiQuestionService {

    @Value("${s3.qna.path}")
    private String PATH;

    private final UserService userService;
    private final QuestionService questionService;
    private final QuestionImageService questionImageService;

    private final S3Uploader s3Uploader;

    public QuestionCreateDto.Response createQuestion(QuestionCreateDto.Request request, String email) {

        // 유저 조회
        User user = userService.getUserByEmail(Email.of(email));

        // 질문 생성
        Question question = Question.createQuestion(request.getTitle(), request.getContent(), request.getMbti(), user);

        // 질문 저장
        questionService.create(question);

        // 질문 이미지 생성 및 저장
        List<QuestionImage> questionImages = request.getQuestionImgs().stream().map(multipartFile -> {
            String fileName = s3Uploader.uploadFile(multipartFile, PATH);
            return QuestionImage.createQuestionImage(question, fileName);
        }).collect(Collectors.toList());

        questionImageService.save(questionImages);

        return QuestionCreateDto.Response.of(question);

    }
}
