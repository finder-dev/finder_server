package com.cmc.finder.api.qna.answer.api;

import com.cmc.finder.api.qna.answer.dto.AnswerCreateDto;
import com.cmc.finder.api.qna.answer.dto.HelpfulAddOrDeleteDto;
import com.cmc.finder.api.qna.answer.service.ApiAnswerService;
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

    @PostMapping("/questions/{questionId}")
    public ResponseEntity<AnswerCreateDto.Response> createAnswer(
            @PathVariable Long questionId,
            @Valid AnswerCreateDto.Request request,
            @UserEmail String email
    ) {

        AnswerCreateDto.Response response = apiAnswerService.createAnswer(questionId, request, email);
        return ResponseEntity.ok(response);

    }

    @PostMapping("/{answerId}/helpful")
    public ResponseEntity<HelpfulAddOrDeleteDto> addOrDeleteHelpful(
            @PathVariable Long answerId,
            @UserEmail String email
    ){

        HelpfulAddOrDeleteDto response = apiAnswerService.addOrDeleteHelpful(answerId, email);
        return ResponseEntity.ok(response);
    }






}
