package com.cmc.finder.api.debate.api;

import com.cmc.finder.api.debate.application.service.ApiDebateAnswerService;
import com.cmc.finder.api.debate.dto.*;
import com.cmc.finder.global.resolver.UserEmail;
import com.cmc.finder.global.response.ApiResult;
import com.cmc.finder.global.util.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/debate")
public class DebateAnswerApi {

    private final ApiDebateAnswerService apiDebateAnswerService;

    @PostMapping("/{debateId}/answers")
    public ResponseEntity<ApiResult<CreateDebateAnswerDto.Response>> createDebateAnswer(
            @PathVariable Long debateId,
            @RequestBody @Valid CreateDebateAnswerDto.Request request,
            @UserEmail String email
    ){

        CreateDebateAnswerDto.Response response = apiDebateAnswerService.createDebateAnswer(debateId, request, email);
        return ResponseEntity.ok(ApiUtils.success(response));

    }


    @DeleteMapping("/answers/{answerId}")
    public ResponseEntity<ApiResult<DeleteDebateAnswerRes>> deleteDebateAnswer(
            @PathVariable Long answerId,
            @UserEmail String email
    ) {

        DeleteDebateAnswerRes response = apiDebateAnswerService.deleteDebateAnswer(answerId, email);
        return ResponseEntity.ok(ApiUtils.success(response));
    }




    @PostMapping("/answers/{answerId}/reply")
    public ResponseEntity<ApiResult<CreateDebateReplyDto.Response>> createDebateReply(
            @RequestBody @Valid CreateDebateReplyDto.Request request,
            @PathVariable Long answerId,
            @UserEmail String email
    ) {
        CreateDebateReplyDto.Response response = apiDebateAnswerService.createDebateReply(answerId, request, email);
        return ResponseEntity.ok(ApiUtils.success(response));

    }

    @PatchMapping("/answers/{answerId}")
    public ResponseEntity<ApiResult<UpdateDebateAnswerDto.Response>> updateDebateAnswer(
            @RequestBody @Valid UpdateDebateAnswerDto.Request request,
            @PathVariable Long answerId,
            @UserEmail String email
    ) {
        UpdateDebateAnswerDto.Response response = apiDebateAnswerService.updateDebateAnswer(answerId, email, request);
        return ResponseEntity.ok(ApiUtils.success(response));

    }

    @PostMapping("/answers/{debateAnswerId}/report")
    public ResponseEntity<ApiResult<ReportDebateRes>> reportAnswer(
            @PathVariable Long debateAnswerId,
            @UserEmail String email
    ){
        ReportDebateRes response = apiDebateAnswerService.reportAnswer(debateAnswerId, email);
        return ResponseEntity.ok(ApiUtils.success(response));

    }


}
