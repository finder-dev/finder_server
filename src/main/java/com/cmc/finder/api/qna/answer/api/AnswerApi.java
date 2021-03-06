package com.cmc.finder.api.qna.answer.api;

import com.cmc.finder.api.qna.answer.dto.*;
import com.cmc.finder.api.qna.answer.application.ApiAnswerService;
import com.cmc.finder.global.resolver.UserEmail;
import com.cmc.finder.global.response.ApiResult;
import com.cmc.finder.global.util.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/answers")
public class AnswerApi {

    private final ApiAnswerService apiAnswerService;


    @PostMapping("/questions/{questionId}")
    public ResponseEntity<ApiResult<AnswerCreateDto.Response>> createAnswer(
            @PathVariable Long questionId,
            @Valid AnswerCreateDto.Request request,
            @UserEmail String email
    ) {

        AnswerCreateDto.Response response = apiAnswerService.createAnswer(questionId, request, email);
        return ResponseEntity.ok(ApiUtils.success(response));

    }

    @PostMapping("/{answerId}/helpful")
    public ResponseEntity<ApiResult<AddOrDeleteHelpfulRes>> addOrDeleteHelpful(
            @PathVariable Long answerId,
            @UserEmail String email
    ) {

        AddOrDeleteHelpfulRes response = apiAnswerService.addOrDeleteHelpful(answerId, email);
        return ResponseEntity.ok(ApiUtils.success(response));
    }

//    @PatchMapping("/{answerId}}")
//    public ResponseEntity<ApiResult<ReplyUpdateDto.Response>> updateReply(
//            @Valid ReplyUpdateDto.Request request,
//            @PathVariable Long replyId,
//            @UserEmail String email
//    ) {
//
//        ReplyUpdateDto.Response response = apiAnswerService.updateReply(request, replyId, email);
//        return ResponseEntity.ok(ApiUtils.success(response));
//
//    }

    @DeleteMapping("/{answerId}")
    public ResponseEntity<ApiResult<DeleteAnswerRes>> deleteAnswer(
            @PathVariable Long answerId,
            @UserEmail String email

    ) {

        DeleteAnswerRes response = apiAnswerService.deleteAnswer(answerId, email);
        return ResponseEntity.ok(ApiUtils.success(response));

    }

    @PostMapping("/{answerId}/reply")
    public ResponseEntity<ApiResult<ReplyCreateDto.Response>> createReply(
            @Valid ReplyCreateDto.Request request,
            @PathVariable Long answerId,
            @UserEmail String email
    ) {

        ReplyCreateDto.Response response = apiAnswerService.createReply(answerId, request, email);
        return ResponseEntity.ok(ApiUtils.success(response));

    }

    @GetMapping("/{answerId}/reply")
    public ResponseEntity<ApiResult<List<GetReplyRes>>> getReply(
            @PathVariable Long answerId
    ) {

        List<GetReplyRes> response = apiAnswerService.getReply(answerId);
        return ResponseEntity.ok(ApiUtils.success(response));

    }

    @PatchMapping("/reply/{replyId}")
    public ResponseEntity<ApiResult<ReplyUpdateDto.Response>> updateReply(
            @Valid ReplyUpdateDto.Request request,
            @PathVariable Long replyId,
            @UserEmail String email
    ) {

        ReplyUpdateDto.Response response = apiAnswerService.updateReply(request, replyId, email);
        return ResponseEntity.ok(ApiUtils.success(response));

    }

    @DeleteMapping("/reply/{replyId}")
    public ResponseEntity<ApiResult<DeleteReplyRes>> deleteReply(
            @PathVariable Long replyId,
            @UserEmail String email
    ) {

        DeleteReplyRes response = apiAnswerService.deleteReply(replyId, email);
        return ResponseEntity.ok(ApiUtils.success(response));

    }




}
