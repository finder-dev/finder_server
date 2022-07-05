package com.cmc.finder.api.community.api;

import com.cmc.finder.api.community.application.ApiCommunityService;
import com.cmc.finder.api.community.dto.*;
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
    public ResponseEntity<ApiResult<Page<CommunitySimpleDto.Response>>> getCommunities(
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

        Page<CommunitySimpleDto.Response> response = apiCommunityService.getCommunityList(pageable, request.getMbti());
        return ResponseEntity.ok(ApiUtils.success(response));

    }


    @GetMapping("/hot")
    public ResponseEntity<ApiResult<List<GetHotCommunityRes>>> getHotCommunities() {

        List<GetHotCommunityRes> response = apiCommunityService.getHotCommunity();
        return ResponseEntity.ok(ApiUtils.success(response));
    }

    @GetMapping("/{communityId}")
    public ResponseEntity<ApiResult<CommunityDetailDto>> getCommunityDetail(
            @PathVariable Long communityId,
            @UserEmail String email
    ) {

        CommunityDetailDto response = apiCommunityService.getCommunityDetail(communityId, email);
        return ResponseEntity.ok(ApiUtils.success(response));

    }

    @PutMapping("/{communityId}")
    public ResponseEntity<ApiResult<UpdateCommunityDto.Response>> updateCommunity(
            @PathVariable Long communityId,
            @Valid UpdateCommunityDto.Request request,
            @UserEmail String email
    ) {

        UpdateCommunityDto.Response response = apiCommunityService.updateCommunity(communityId, request, email);
        return ResponseEntity.ok(ApiUtils.success(response));
    }

    @DeleteMapping("/{communityId}")
    public ResponseEntity<ApiResult<DeleteCommunityRes>> deleteQuestion(
            @PathVariable Long communityId,
            @UserEmail String email
    ) {

        DeleteCommunityRes response = apiCommunityService.deleteCommunity(communityId, email);
        return ResponseEntity.ok(ApiUtils.success(response));
    }

    @PostMapping("/{communityId}/like")
    public ResponseEntity<ApiResult<AddOrDeleteLikeRes>> addOrDeleteCurious(
            @PathVariable Long communityId,
            @UserEmail String email
    ) {

        AddOrDeleteLikeRes response = apiCommunityService.addOrDeleteLike(communityId, email);
        return ResponseEntity.ok(ApiUtils.success(response));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResult<Page<CommunitySearchDto.Response>>> searchCommunity(
            @Valid CommunitySearchDto.Request request,
            Optional<Integer> page
    ) {

        Pageable pageable = PageRequest.of(
                page.isPresent() ? page.get() : 0,
                SET_PAGE_ITEM_MAX_COUNT,
                request.getOrderBy() == null ?
                        Sort.by(Sort.Direction.DESC, OrderBy.CREATE_TIME.name()) :
                        Sort.by(Sort.Direction.DESC, request.getOrderBy())
        );

        Page<CommunitySearchDto.Response> response = apiCommunityService.searchCommunity(request.getSearchQuery(), pageable);
        return ResponseEntity.ok(ApiUtils.success(response));
    }


}
