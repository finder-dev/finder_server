package com.cmc.finder.api.debate.api;

import com.cmc.finder.api.debate.dto.DebateCreateDto;
import com.cmc.finder.api.debate.dto.DebateSimpleDto;
import com.cmc.finder.api.debate.dto.JoinDebateDto;
import com.cmc.finder.api.debate.service.ApiDebateService;
import com.cmc.finder.domain.model.MBTI;
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
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/debates")
public class DebateApi {

//    @Value("${page.count}")
    private final Integer SET_PAGE_ITEM_MAX_COUNT = 10;

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
    public ResponseEntity<ApiResult<JoinDebateDto.Response>> joinOrDetachDebate(
            @PathVariable Long debateId,
            @Valid JoinDebateDto.Request request,
            @UserEmail String email
    ) {

        JoinDebateDto.Response response = apiDebateService.joinOrDetachDebate(request,debateId, email);
        return ResponseEntity.ok(ApiUtils.success(response));
    }

//    @GetMapping
//    public ResponseEntity<Page<DebateSimpleDto.Response>> getDebates(
//            @Valid DebateSimpleDto.Request request,
//            Optional<Integer> page
//    ) {
//
//        Pageable pageable = PageRequest.of(
//                page.isPresent() ? page.get() : 0,
//                SET_PAGE_ITEM_MAX_COUNT);
//
//        Page<DebateSimpleDto.Response> questionSimpleDtos = apiDebateService.getDebateList(pageable);
//        return ResponseEntity.ok(questionSimpleDtos);
//
//    }


}
