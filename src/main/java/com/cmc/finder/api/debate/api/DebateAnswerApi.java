package com.cmc.finder.api.debate.api;

import com.cmc.finder.api.community.dto.CreateCommunityReplyDto;
import com.cmc.finder.api.debate.application.ApiDebateAnswerService;
import com.cmc.finder.api.debate.application.ApiDebateService;
import com.cmc.finder.api.debate.dto.*;
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
@RequestMapping("/api/debate")
public class DebateAnswerApi {

    private final ApiDebateAnswerService apiDebateAnswerService;

    @PostMapping("/{debateId}/answers")
    public ResponseEntity<ApiResult<DebateAnswerCreateDto.Response>> createDebateAnswer(
            @PathVariable Long debateId,
            @Valid DebateAnswerCreateDto.Request request,
            @UserEmail String email
    ){

        DebateAnswerCreateDto.Response response = apiDebateAnswerService.createDebateAnswer(debateId, request, email);
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

        DebateAnswerDeleteDto response = apiDebateAnswerService.deleteDebateAnswer(debateAnswerId, email);
        return ResponseEntity.ok(ApiUtils.success(response));
    }




    @PostMapping("/debate-answer/{debateAnswerId}/debate-reply")
    public ResponseEntity<ApiResult<DebateReplyCreateDto.Response>> createDebateReply(
            @Valid DebateReplyCreateDto.Request request,
            @PathVariable Long debateAnswerId,
            @UserEmail String email
    ) {

        DebateReplyCreateDto.Response response = apiDebateAnswerService.createDebateReply(debateAnswerId, request, email);
        return ResponseEntity.ok(ApiUtils.success(response));

    }

//
//    @GetMapping("/debate-answer/{debateAnswerId}/debate-reply")
//    public ResponseEntity<ApiResult<List<GetDebateReplyRes>>> getDebateReply(
//            @PathVariable Long debateAnswerId
//    ) {
//
//        List<GetDebateReplyRes> response = apiDebateService.getDebateReply(debateAnswerId);
//        return ResponseEntity.ok(ApiUtils.success(response));
//
//    }

//    @PatchMapping("/debate-answer/debate-reply/{debateReplyId}")
//    public ResponseEntity<ApiResult<DebateReplyUpdateDto.Response>> updateDebateReply(
//            @Valid DebateReplyUpdateDto.Request request,
//            @PathVariable Long debateReplyId,
//            @UserEmail String email
//    ) {
//
//        DebateReplyUpdateDto.Response response = apiDebateAnswerService.updateDebateReply(request, debateReplyId, email);
//        return ResponseEntity.ok(ApiUtils.success(response));
//
//    }
//
//    @DeleteMapping("/debate-answer/debate-reply/{debateReplyId}")
//    public ResponseEntity<ApiResult<DeleteDebateReplyRes>> deleteDebateReply(
//            @PathVariable Long debateReplyId,
//            @UserEmail String email
//    ) {
//
//        DeleteDebateReplyRes response = apiDebateAnswerService.deleteDebateReply(debateReplyId, email);
//        return ResponseEntity.ok(ApiUtils.success(response));
//
//    }



}
