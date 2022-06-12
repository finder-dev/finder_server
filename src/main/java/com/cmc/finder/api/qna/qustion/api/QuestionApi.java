package com.cmc.finder.api.qna.qustion.api;

import com.cmc.finder.api.qna.qustion.dto.QuestionCreateDto;
import com.cmc.finder.api.qna.qustion.dto.QuestionSimpleDto;
import com.cmc.finder.api.qna.qustion.service.ApiQuestionService;
import com.cmc.finder.global.resolver.UserEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/questions")
public class QuestionApi {

    private final Integer SET_PAGE_ITEM_MAX_COUNT = 10;
    private final ApiQuestionService apiQuestionService;

    @PostMapping
    public ResponseEntity<QuestionCreateDto.Response> createQuestion(
            @Valid QuestionCreateDto.Request request,
            @UserEmail String email
    ) {

        QuestionCreateDto.Response response = apiQuestionService.createQuestion(request, email);
        return ResponseEntity.ok(response);

    }

    @GetMapping
    public ResponseEntity<Page<QuestionSimpleDto>> getQuestion(
//            FilteringDto filteringDto,
            Optional<Integer> page
    ) {

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, SET_PAGE_ITEM_MAX_COUNT);
        Page<QuestionSimpleDto> questionSimpleDtos = apiQuestionService.getQuestionList(pageable);

        return ResponseEntity.ok(questionSimpleDtos);

    }




}
