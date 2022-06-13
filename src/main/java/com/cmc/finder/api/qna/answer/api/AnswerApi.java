package com.cmc.finder.api.qna.answer.api;

import com.cmc.finder.api.qna.answer.dto.AnswerCreateDto;
import com.cmc.finder.api.qna.answer.service.ApiAnswerService;
import com.cmc.finder.api.qna.qustion.dto.QuestionCreateDto;
import com.cmc.finder.global.resolver.UserEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/answers")
public class AnswerApi {

    private final ApiAnswerService apiAnswerService;

    @PostMapping("/{questionId}")
    public ResponseEntity<AnswerCreateDto.Response> createQuestion(
            @PathVariable Long questionId,
            @Valid AnswerCreateDto.Request request,
            @UserEmail String email
    ) {

        AnswerCreateDto.Response response = apiAnswerService.createAnswer(questionId, request, email);
        return ResponseEntity.ok(response);

    }



}
