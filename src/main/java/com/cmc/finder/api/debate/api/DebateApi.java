package com.cmc.finder.api.debate.api;

import com.cmc.finder.api.debate.dto.*;
import com.cmc.finder.api.debate.service.ApiDebateService;
import com.cmc.finder.api.qna.answer.dto.DeleteReplyRes;
import com.cmc.finder.api.qna.answer.dto.GetReplyRes;
import com.cmc.finder.api.qna.answer.dto.ReplyCreateDto;
import com.cmc.finder.api.qna.answer.dto.ReplyUpdateDto;
import com.cmc.finder.global.resolver.UserEmail;
import com.cmc.finder.global.response.ApiResult;
import com.cmc.finder.global.util.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/debates")
public class DebateApi {

    @Value("${page.count}")
    private Integer SET_PAGE_ITEM_MAX_COUNT;

    private final ApiDebateService apiDebateService;

    @PostMapping
    public ResponseEntity<ApiResult<DebateCreateDto.Response>> createDebate(
            @Valid DebateCreateDto.Request request,
            @UserEmail String email
    ) {

        DebateCreateDto.Response response = apiDebateService.createDebate(request, email);
        return ResponseEntity.ok(ApiUtils.success(response));
    }

    @PostMapping("/{debateId}")
    public ResponseEntity<ApiResult<DebateJoinDto.Response>> joinOrDetachDebate(
            @PathVariable Long debateId,
            @Valid DebateJoinDto.Request request,
            @UserEmail String email
    ) {

        DebateJoinDto.Response response = apiDebateService.joinOrDetachDebate(request,debateId, email);
        return ResponseEntity.ok(ApiUtils.success(response));
    }

    @GetMapping
    public ResponseEntity<ApiResult<Page<DebateSimpleDto.Response>>> getDebates(
            @Valid DebateSimpleDto.Request request,
            Optional<Integer> page
    ) {

        Pageable pageable = PageRequest.of(
                page.isPresent() ? page.get() : 0,
                SET_PAGE_ITEM_MAX_COUNT);

        Page<DebateSimpleDto.Response> response = apiDebateService.getDebateList(request, pageable);
        return ResponseEntity.ok(ApiUtils.success(response));

    }

    @GetMapping("/hot")
    public ResponseEntity<ApiResult<GetHotDebateRes>> getHotDebate(
            @UserEmail String email
    ) {

        GetHotDebateRes response = apiDebateService.getHotDebate(email);
        return ResponseEntity.ok(ApiUtils.success(response));
    }

    @GetMapping("/{debateId}")
    public ResponseEntity<ApiResult<DebateDetailDto>> getDebateDetail(
            @PathVariable Long debateId
    ) {

        DebateDetailDto debateDetailDto = apiDebateService.getDebateDetail(debateId);
        return ResponseEntity.ok(ApiUtils.success(debateDetailDto));

    }

    @PostMapping("/{debateId}/answers")
    public ResponseEntity<ApiResult<DebateAnswerCreateDto.Response>> createDebateAnswer(
            @PathVariable Long debateId,
            @Valid DebateAnswerCreateDto.Request request,
            @UserEmail String email
    ){

        DebateAnswerCreateDto.Response response = apiDebateService.createDebateAnswer(debateId, request, email);
        return ResponseEntity.ok(ApiUtils.success(response));

    }

//    //TODO 토론 삭제는 없나?
//    @DeleteMapping("/{debateId}")
//    public ResponseEntity<ApiResult<DebateDeleteDto>> deleteDebate(
//            @PathVariable Long debateId,
//            @UserEmail String email
//    ) {
//
//        DebateDeleteDto response = apiDebateService.deleteDebate(debateId, email);
//        return ResponseEntity.ok(ApiUtils.success(response));
//    }

    @DeleteMapping("/debate-answer/{debateAnswerId}")
    public ResponseEntity<ApiResult<DebateAnswerDeleteDto>> deleteDebateAnswer(
            @PathVariable Long debateAnswerId,
            @UserEmail String email
    ) {

        DebateAnswerDeleteDto response = apiDebateService.deleteDebateAnswer(debateAnswerId, email);
        return ResponseEntity.ok(ApiUtils.success(response));
    }



    @PostMapping("/debate-answer/{debateAnswerId}/debate-reply")
    public ResponseEntity<ApiResult<DebateReplyCreateDto.Response>> createDebateReply(
            @Valid DebateReplyCreateDto.Request request,
            @PathVariable Long debateAnswerId,
            @UserEmail String email
    ) {

        DebateReplyCreateDto.Response response = apiDebateService.createDebateReply(debateAnswerId, request, email);
        return ResponseEntity.ok(ApiUtils.success(response));

    }

    @GetMapping("/debate-answer/{debateAnswerId}/debate-reply")
    public ResponseEntity<ApiResult<List<GetDebateReplyRes>>> getDebateReply(
            @PathVariable Long debateAnswerId
    ) {

        List<GetDebateReplyRes> response = apiDebateService.getDebateReply(debateAnswerId);
        return ResponseEntity.ok(ApiUtils.success(response));

    }

    @PatchMapping("/debate-answer/debate-reply/{debateReplyId}")
    public ResponseEntity<ApiResult<DebateReplyUpdateDto.Response>> updateDebateReply(
            @Valid DebateReplyUpdateDto.Request request,
            @PathVariable Long debateReplyId,
            @UserEmail String email
    ) {

        DebateReplyUpdateDto.Response response = apiDebateService.updateDebateReply(request, debateReplyId, email);
        return ResponseEntity.ok(ApiUtils.success(response));

    }

    @DeleteMapping("/debate-answer/debate-reply/{debateReplyId}")
    public ResponseEntity<ApiResult<DeleteDebateReplyRes>> deleteDebateReply(
            @PathVariable Long debateReplyId,
            @UserEmail String email
    ) {

        DeleteDebateReplyRes response = apiDebateService.deleteDebateReply(debateReplyId, email);
        return ResponseEntity.ok(ApiUtils.success(response));

    }



}
