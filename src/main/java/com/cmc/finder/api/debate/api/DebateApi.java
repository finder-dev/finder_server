package com.cmc.finder.api.debate.api;

import com.cmc.finder.api.debate.dto.*;
import com.cmc.finder.api.debate.application.service.ApiDebateService;
import com.cmc.finder.global.resolver.UserEmail;
import com.cmc.finder.global.response.ApiResult;
import com.cmc.finder.global.util.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/debate")
public class DebateApi {

    @Value("${page.count}")
    private Integer SET_PAGE_ITEM_MAX_COUNT;

    private final ApiDebateService apiDebateService;

    @PostMapping
    public ResponseEntity<ApiResult<CreateDebateDto.Response>> createDebate(
            @RequestBody @Valid CreateDebateDto.Request request,
            @UserEmail String email
    ) {
        CreateDebateDto.Response response = apiDebateService.createDebate(request, email);
        return ResponseEntity.ok(ApiUtils.success(response));
    }

    @PostMapping("/{debateId}")
    public ResponseEntity<ApiResult<JoinDebateDto.Response>> joinOrDetachDebate(
            @PathVariable Long debateId,
            @RequestBody @Valid JoinDebateDto.Request request,
            @UserEmail String email
    ) {

        JoinDebateDto.Response response = apiDebateService.joinOrDetachDebate(request,debateId, email);
        return ResponseEntity.ok(ApiUtils.success(response));
    }

    @GetMapping
    public ResponseEntity<ApiResult<Slice<DebateSimpleDto.Response>>> getDebateList(
            @Valid DebateSimpleDto.Request request,
            @UserEmail String email,
            Optional<Integer> page
    ) {
        Pageable pageable = PageRequest.of(
                page.isPresent() ? page.get() : 0,
                SET_PAGE_ITEM_MAX_COUNT);

        Slice<DebateSimpleDto.Response> response = apiDebateService.getDebateList(request,email, pageable);

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
            @PathVariable Long debateId,
            @UserEmail String email

    ) {
        DebateDetailDto debateDetailDto = apiDebateService.getDebateDetail(debateId, email);
        return ResponseEntity.ok(ApiUtils.success(debateDetailDto));

    }

    @PostMapping("/{debateId}/report")
    public ResponseEntity<ApiResult<ReportDebateRes>> reportDebate(
            @PathVariable Long debateId,
            @UserEmail String email
    ){
        ReportDebateRes response = apiDebateService.reportDebate(debateId, email);
        return ResponseEntity.ok(ApiUtils.success(response));

    }

}
