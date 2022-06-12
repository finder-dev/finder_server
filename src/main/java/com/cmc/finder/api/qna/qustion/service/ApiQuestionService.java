package com.cmc.finder.api.qna.qustion.service;

import com.cmc.finder.api.qna.qustion.dto.QuestionCreateDto;
import com.cmc.finder.api.qna.qustion.dto.QuestionSimpleDto;
import com.cmc.finder.api.qna.qustion.repository.QuestionRepositoryCustom;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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
    private final QuestionRepositoryCustom questionRepositoryCustom;

    private final S3Uploader s3Uploader;

    public QuestionCreateDto.Response createQuestion(QuestionCreateDto.Request request, String email) {

        // 유저 조회
        User user = userService.getUserByEmail(Email.of(email));

        // 질문 생성
        Question question = Question.createQuestion(request.getTitle(), request.getContent(), request.getMbti(), user);

        // 질문 저장
        questionService.create(question);

        // 질문 이미지 생성 및 저장

        List<QuestionImage> questionImages = new ArrayList<>();
        for (int i = 0; i < request.getQuestionImgs().size(); i++) {

            MultipartFile multipartFile = request.getQuestionImgs().get(i);

            String imageName = s3Uploader.uploadFile(multipartFile, PATH);
            String url = s3Uploader.getUrl(PATH, imageName);
            QuestionImage questionImage;
            if (i != 0) {
                questionImage = QuestionImage.createQuestionImage(question, imageName, url, false);
            }else {
                questionImage = QuestionImage.createQuestionImage(question, imageName, url, true);
            }
            questionImages.add(questionImage);
        }



//        List<QuestionImage> questionImages = request.getQuestionImgs().stream().map(multipartFile -> {
//            String imageName = s3Uploader.uploadFile(multipartFile, PATH);
//            String url = s3Uploader.getUrl(PATH, imageName);
//            return QuestionImage.createQuestionImage(question, imageName, url);
//        }).collect(Collectors.toList());

        questionImageService.save(questionImages);

        return QuestionCreateDto.Response.of(question);

    }

    public Page<QuestionSimpleDto> getQuestionList(Pageable pageable) {

        Page<QuestionSimpleDto> questionSimpleDto = questionRepositoryCustom.findQuestionSimpleDto(pageable);
        return questionSimpleDto;
    }
}
