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
            @Valid CreateDebateAnswerDto.Request request,
            @UserEmail String email
    ){

        CreateDebateAnswerDto.Response response = apiDebateAnswerService.createDebateAnswer(debateId, request, email);
        return ResponseEntity.ok(ApiUtils.success(response));

    }


    @DeleteMapping("/answers/{debateAnswerId}")
    public ResponseEntity<ApiResult<DeleteDebateAnswerRes>> deleteDebateAnswer(
            @PathVariable Long debateAnswerId,
            @UserEmail String email
    ) {

        DeleteDebateAnswerRes response = apiDebateAnswerService.deleteDebateAnswer(debateAnswerId, email);
        return ResponseEntity.ok(ApiUtils.success(response));
    }




    @PostMapping("/answers/{debateAnswerId}/reply")
    public ResponseEntity<ApiResult<CreateDebateReplyDto.Response>> createDebateReply(
            @Valid CreateDebateReplyDto.Request request,
            @PathVariable Long debateAnswerId,
            @UserEmail String email
    ) {

        CreateDebateReplyDto.Response response = apiDebateAnswerService.createDebateReply(debateAnswerId, request, email);
        return ResponseEntity.ok(ApiUtils.success(response));

    }

    @PatchMapping("/answers/{debateAnswerId}")
    public ResponseEntity<ApiResult<UpdateDebateAnswerDto.Response>> updateDebateAnswer(
            @Valid UpdateDebateAnswerDto.Request request,
            @PathVariable Long debateAnswerId,
            @UserEmail String email
    ) {

        UpdateDebateAnswerDto.Response response = apiDebateAnswerService.updateDebateAnswer(debateAnswerId, email, request);
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
