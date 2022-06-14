package com.cmc.finder.api.qna.answer.service;

import com.cmc.finder.api.qna.answer.dto.AnswerCreateDto;
import com.cmc.finder.api.qna.qustion.dto.QuestionCreateDto;
import com.cmc.finder.api.qna.qustion.dto.QuestionDetailDto;
import com.cmc.finder.api.qna.qustion.dto.QuestionSimpleDto;
import com.cmc.finder.api.qna.qustion.repository.QuestionRepositoryCustom;
import com.cmc.finder.domain.answer.entity.Answer;
import com.cmc.finder.domain.answer.entity.AnswerImage;
import com.cmc.finder.domain.answer.service.AnswerService;
import com.cmc.finder.domain.model.Email;
import com.cmc.finder.domain.question.entity.Question;
import com.cmc.finder.domain.question.entity.QuestionImage;
import com.cmc.finder.domain.question.service.QuestionService;
import com.cmc.finder.domain.user.entity.User;
import com.cmc.finder.domain.user.service.UserService;
import com.cmc.finder.infra.file.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ApiAnswerService {

    @Value("${s3.answer.path}")
    private String PATH;

    private final UserService userService;
    private final QuestionService questionService;
    private final AnswerService answerService;

    private final S3Uploader s3Uploader;


    public AnswerCreateDto.Response createAnswer(Long questionId, AnswerCreateDto.Request request, String email) {

        // 유저 조회
        User user = userService.getUserByEmail(Email.of(email));

        // 질문 조회
        Question question = questionService.getQuestion(questionId);
        // 답변 생성
        Answer answer = Answer.createAnswer(request.getTitle(), request.getContent(), user, question);
        question.addAnswer(answer);

        // 답변 이미지 생성
        request.getAnswerImgs().stream().forEach(multipartFile -> {
            String imageName = s3Uploader.uploadFile(multipartFile, PATH);
            String url = s3Uploader.getUrl(PATH, imageName);

            AnswerImage answerImage = AnswerImage.createAnswerImage(answer, imageName, url);
            answer.addAnswerImage(answerImage);
        });

        answerService.create(answer);

        return AnswerCreateDto.Response.of(question);

    }
}
