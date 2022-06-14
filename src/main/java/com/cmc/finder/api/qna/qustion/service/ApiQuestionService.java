package com.cmc.finder.api.qna.qustion.service;

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

import java.util.List;
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ApiQuestionService {

    @Value("${s3.question.path}")
    private String PATH;

    private final UserService userService;
    private final QuestionService questionService;
    private final QuestionRepositoryCustom questionRepositoryCustom;

    private final AnswerService answerService;
    private final S3Uploader s3Uploader;

    public QuestionCreateDto.Response createQuestion(QuestionCreateDto.Request request, String email) {

        // 유저 조회
        User user = userService.getUserByEmail(Email.of(email));

        // 질문 생성
        Question question = Question.createQuestion(request.getTitle(), request.getContent(), request.getMbti(), user);

        // 질문 이미지 생성 및 저장
//        List<QuestionImage> questionImages = new ArrayList<>();
        for (int i = 0; i < request.getQuestionImgs().size(); i++) {
            MultipartFile multipartFile = request.getQuestionImgs().get(i);
            String imageName = s3Uploader.uploadFile(multipartFile, PATH);
            String url = s3Uploader.getUrl(PATH, imageName);
            QuestionImage questionImage;
            if (i != 0) {
                questionImage = QuestionImage.createQuestionImage(question, imageName, url, false);
            } else {
                questionImage = QuestionImage.createQuestionImage(question, imageName, url, true);
            }
            question.addQuestionImage(questionImage);
//            questionImages.add(questionImage);
        }

        // 질문 저장
        questionService.create(question);

//        List<QuestionImage> questionImages = request.getQuestionImgs().stream().map(multipartFile -> {
//            String imageName = s3Uploader.uploadFile(multipartFile, PATH);
//            String url = s3Uploader.getUrl(PATH, imageName);
//            return QuestionImage.createQuestionImage(question, imageName, url);
//        }).collect(Collectors.toList());

//        questionImageService.save(questionImages);

        return QuestionCreateDto.Response.of(question);

    }

    public Page<QuestionSimpleDto> getQuestionList(Pageable pageable) {

        Page<QuestionSimpleDto> questionPage = questionRepositoryCustom.findQuestionSimpleDto(pageable);
        return questionPage;

    }

    public QuestionDetailDto getQuestionDetail(Long questionId) {

        // 질문 조회
        Question question = questionService.getQuestionFetchQuestionImage(questionId);

        // 답변 조회
        List<Answer> answers = answerService.getAnswersByQuestionId(question.getQuestionId());

        System.out.println(answers.size());

        QuestionDetailDto questionDetailDto = QuestionDetailDto.of(question, answers);
        return questionDetailDto;


    }
}
