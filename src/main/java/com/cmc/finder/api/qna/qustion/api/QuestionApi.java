package com.cmc.finder.api.qna.qustion.api;

import com.cmc.finder.api.qna.qustion.dto.*;
import com.cmc.finder.api.qna.qustion.application.ApiQuestionService;
import com.cmc.finder.domain.model.MBTI;
import com.cmc.finder.domain.model.OrderBy;
import com.cmc.finder.global.resolver.UserEmail;
import com.cmc.finder.global.response.ApiResult;
import com.cmc.finder.global.util.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/questions")
public class QuestionApi {

    @Value("${page.count}")
    private Integer SET_PAGE_ITEM_MAX_COUNT;

    private final ApiQuestionService apiQuestionService;


    @PostMapping
    public ResponseEntity<ApiResult<CreateQuestionDto.Response>> createQuestion(
            @Valid CreateQuestionDto.Request request,
            @UserEmail String email
    ) {

        CreateQuestionDto.Response response = apiQuestionService.createQuestion(request, email);
        return ResponseEntity.ok(ApiUtils.success(response));

    }

    @GetMapping
    public ResponseEntity<ApiResult<Page<QuestionSimpleDto.Response>>> getQuestions(
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

        Page<QuestionSimpleDto.Response> response = apiQuestionService.getQuestionList(pageable, MBTI.from(request.getMbti()));
        return ResponseEntity.ok(ApiUtils.success(response));

    }

    @GetMapping("/hot")
    public ResponseEntity<ApiResult<List<GetHotQuestionRes>>> getHotQuestion() {

        List<GetHotQuestionRes> response = apiQuestionService.getHotQuestion();
        return ResponseEntity.ok(ApiUtils.success(response));
    }

    @GetMapping("/favorite")
    public ResponseEntity<ApiResult<List<FavoriteQuestionRes>>> getFavoriteQuestion(
            @UserEmail String email
    ){

        List<FavoriteQuestionRes> response = apiQuestionService.getFavoriteQuestion(email);
        return ResponseEntity.ok(ApiUtils.success(response));
    }


    @GetMapping("/{questionId}")
    public ResponseEntity<ApiResult<QuestionDetailDto>> getQuestionDetail(
            @PathVariable Long questionId,
            @UserEmail String email
    ) {

        QuestionDetailDto questionDetailDto = apiQuestionService.getQuestionDetail(questionId, email);
        return ResponseEntity.ok(ApiUtils.success(questionDetailDto));

    }


    @PutMapping("/{questionId}")
    public ResponseEntity<ApiResult<UpdateQuestionDto.Response>> updateQuestion(
            @PathVariable Long questionId,
            @Valid UpdateQuestionDto.Request request,
            @UserEmail String email
    ) {

        UpdateQuestionDto.Response response = apiQuestionService.updateQuestion(questionId, request, email);

        return ResponseEntity.ok(ApiUtils.success(response));
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity<ApiResult<DeleteQuestionRes>> deleteQuestion(
            @PathVariable Long questionId,
            @UserEmail String email
    ) {

        DeleteQuestionRes response = apiQuestionService.deleteQuestion(questionId, email);
        return ResponseEntity.ok(ApiUtils.success(response));
    }


    @PostMapping("/{questionId}/curious")
    public ResponseEntity<ApiResult<AddOrDeleteCuriousRes>> addOrDeleteCurious(
            @PathVariable Long questionId,
            @UserEmail String email
    ){

        AddOrDeleteCuriousRes response = apiQuestionService.addOrDeleteCurious(questionId, email);
        return ResponseEntity.ok(ApiUtils.success(response));
    }

    @PostMapping("/{questionId}/favorite")
    public ResponseEntity<ApiResult<AddOrDeleteFavoriteQuestionRes>> addOrDeleteFavorite(
            @PathVariable Long questionId,
            @UserEmail String email
    ){

        AddOrDeleteFavoriteQuestionRes response = apiQuestionService.addOrDeleteFavorite(questionId, email);
        return ResponseEntity.ok(ApiUtils.success(response));
    }


}
