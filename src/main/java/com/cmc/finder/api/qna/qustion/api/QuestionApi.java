package com.cmc.finder.api.qna.qustion.api;

import com.cmc.finder.api.qna.qustion.dto.QuestionCreateDto;
import com.cmc.finder.api.qna.qustion.service.ApiQuestionService;
import com.cmc.finder.global.resolver.UserEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/questions")
public class QuestionApi {

    private final ApiQuestionService apiQuestionService;

    @PostMapping
    public ResponseEntity<QuestionCreateDto.Response> createQuestion(
            @Valid QuestionCreateDto.Request request,
            @UserEmail String email
    ) {

        QuestionCreateDto.Response response = apiQuestionService.createQuestion(request, email);
        return ResponseEntity.ok(response);

    }




}
