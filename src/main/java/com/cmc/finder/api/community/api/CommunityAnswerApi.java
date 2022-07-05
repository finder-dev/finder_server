package com.cmc.finder.api.community.api;

import com.cmc.finder.api.community.application.ApiCommunityAnswerService;
import com.cmc.finder.api.community.dto.CreateCommunityAnswerDto;
import com.cmc.finder.api.community.dto.CreateCommunityReplyDto;
import com.cmc.finder.api.community.dto.DeleteCommunityAnswerRes;
import com.cmc.finder.api.community.dto.UpdateCommunityAnswerDto;
import com.cmc.finder.global.resolver.UserEmail;
import com.cmc.finder.global.response.ApiResult;
import com.cmc.finder.global.util.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class CommunityAnswerApi {

    private final ApiCommunityAnswerService apiCommunityAnswerService;

    @PostMapping("/{communityId}/answers")
    public ResponseEntity<ApiResult<CreateCommunityAnswerDto.Response>> createCommunityAnswer(
            @PathVariable Long communityId,
            @Valid CreateCommunityAnswerDto.Request request,
            @UserEmail String email
    ){

        CreateCommunityAnswerDto.Response response = apiCommunityAnswerService.createCommunityAnswer(communityId, request, email);
        return ResponseEntity.ok(ApiUtils.success(response));

    }

    @PatchMapping("/answers/{answerId}")
    public ResponseEntity<ApiResult<UpdateCommunityAnswerDto.Response>> updateAnswer(
            @Valid UpdateCommunityAnswerDto.Request request,
            @PathVariable Long answerId,
            @UserEmail String email
    ) {

        UpdateCommunityAnswerDto.Response response = apiCommunityAnswerService.updateAnswer(request, answerId, email);
        return ResponseEntity.ok(ApiUtils.success(response));

    }


    @DeleteMapping("/answers/{answerId}")
    public ResponseEntity<ApiResult<DeleteCommunityAnswerRes>> deleteAnswer(
            @PathVariable Long answerId,
            @UserEmail String email

    ) {

        DeleteCommunityAnswerRes response = apiCommunityAnswerService.deleteAnswer(answerId, email);
        return ResponseEntity.ok(ApiUtils.success(response));

    }

    @PostMapping("/answers/{answerId}/reply")
    public ResponseEntity<ApiResult<CreateCommunityReplyDto.Response>> createCommunityReply(
            @PathVariable Long answerId,
            @Valid CreateCommunityReplyDto.Request request,
            @UserEmail String email
    ){

        CreateCommunityReplyDto.Response response = apiCommunityAnswerService.createCommunityReply(answerId, request, email);
        return ResponseEntity.ok(ApiUtils.success(response));

    }



}