package com.cmc.finder.api.community.api;

import com.cmc.finder.api.community.application.ApiCommunityService;
import com.cmc.finder.api.community.dto.CommunitySimpleDto;
import com.cmc.finder.api.community.dto.CreateCommunityDto;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class CommunityApi {

    @Value("${page.count}")
    private Integer SET_PAGE_ITEM_MAX_COUNT;

    private final ApiCommunityService apiCommunityService;

    @PostMapping
    public ResponseEntity<ApiResult<CreateCommunityDto.Response>> createCommunity(
            @Valid CreateCommunityDto.Request request,
            @UserEmail String email
    ) {

        CreateCommunityDto.Response response = apiCommunityService.createCommunity(request, email);
        return ResponseEntity.ok(ApiUtils.success(response));

    }

    @GetMapping
    public ResponseEntity<ApiResult<Page<CommunitySimpleDto.Response>>> getQuestions(
            @Valid CommunitySimpleDto.Request request,
            Optional<Integer> page
    ) {

        Pageable pageable = PageRequest.of(
                page.isPresent() ? page.get() : 0,
                SET_PAGE_ITEM_MAX_COUNT,
                request.getOrderBy() == null ?
                        Sort.by(Sort.Direction.DESC, OrderBy.CREATE_TIME.name()) :
                        Sort.by(Sort.Direction.DESC, request.getOrderBy())
        );

        Page<CommunitySimpleDto.Response> response = apiCommunityService.getCommunityList(pageable, MBTI.from(request.getMbti()));
        return ResponseEntity.ok(ApiUtils.success(response));

    }
}
