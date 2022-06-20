package com.cmc.finder.api.qna.qustion.api;

import com.cmc.finder.api.qna.qustion.dto.*;
import com.cmc.finder.api.qna.qustion.service.ApiQuestionService;
import com.cmc.finder.domain.model.MBTI;
import com.cmc.finder.domain.question.constant.OrderBy;
import com.cmc.finder.global.resolver.UserEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<Page<QuestionSimpleDto.Response>> getQuestion(
            @Valid QuestionSimpleDto.Request request,
            Optional<Integer> page
    ) {


        Pageable pageable = PageRequest.of(
                page.isPresent() ? page.get() : 0,
                SET_PAGE_ITEM_MAX_COUNT,
                request.getOrderBy() == null ?
                        Sort.by(Sort.Direction.DESC, OrderBy.CREATE_TIME.name()) :
                        Sort.by(Sort.Direction.DESC, request.getOrderBy())
        );

        Page<QuestionSimpleDto.Response> questionSimpleDtos = apiQuestionService.getQuestionList(pageable, MBTI.from(request.getMbti()));
        return ResponseEntity.ok(questionSimpleDtos);

    }

    @GetMapping("/{questionId}")
    public ResponseEntity<QuestionDetailDto> getQuestionDetail(
            @PathVariable Long questionId,
            @UserEmail String email
    ) {

        QuestionDetailDto questionDetailDto = apiQuestionService.getQuestionDetail(questionId, email);
        return ResponseEntity.ok(questionDetailDto);

    }

    @PutMapping("/{questionId}")
    public ResponseEntity<QuestionUpdateDto.Response> updateQuestion(
            @PathVariable Long questionId,
            QuestionUpdateDto.Request request,
            @UserEmail String email
    ) {

        apiQuestionService.updateQuestion(questionId, request, email);



    }


    @PostMapping("/{questionId}/curious")
    public ResponseEntity<CuriousAddOrDeleteDto> addOrDeleteCurious(
            @PathVariable Long questionId,
            @UserEmail String email
    ){
        CuriousAddOrDeleteDto response = apiQuestionService.addOrDeleteCurious(questionId, email);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{questionId}/favorite")
    public ResponseEntity<QuestionFavoriteAddOrDeleteDto> addOrDeleteFavorite(
            @PathVariable Long questionId,
            @UserEmail String email
    ){

        QuestionFavoriteAddOrDeleteDto response = apiQuestionService.addOrDeleteFavorite(questionId, email);
        return ResponseEntity.ok(response);
    }



}
