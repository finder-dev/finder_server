package com.cmc.finder.api.community.api;

import com.cmc.finder.api.community.application.service.ApiCommunityService;
import com.cmc.finder.api.community.dto.*;
import com.cmc.finder.domain.model.OrderBy;
import com.cmc.finder.global.resolver.UserEmail;
import com.cmc.finder.global.response.ApiResult;
import com.cmc.finder.global.util.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
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
    public ResponseEntity<ApiResult<Slice<CommunitySimpleDto.Response>>> getCommunityList(
            @UserEmail String email,
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

        Slice<CommunitySimpleDto.Response> response = apiCommunityService.getCommunityList(pageable, request.getMbti(), email);
        return ResponseEntity.ok(ApiUtils.success(response));

    }


    @GetMapping("/hot")
    public ResponseEntity<ApiResult<List<GetHotCommunityRes>>> getHotCommunityList(
            @UserEmail String email

    ) {

        List<GetHotCommunityRes> response = apiCommunityService.getHotCommunityList(email);
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

    @PatchMapping("/{communityId}")
    public ResponseEntity<ApiResult<UpdateCommunityDto.Response>> updateCommunity(
            @PathVariable Long communityId,
            @Valid UpdateCommunityDto.Request request,
            @UserEmail String email
    ) {

        UpdateCommunityDto.Response response = apiCommunityService.updateCommunity(communityId, email, request);
        return ResponseEntity.ok(ApiUtils.success(response));
    }

    @DeleteMapping("/{communityId}")
    public ResponseEntity<ApiResult<DeleteCommunityRes>> deleteCommunity(
            @PathVariable Long communityId,
            @UserEmail String email
    ) {

        DeleteCommunityRes response = apiCommunityService.deleteCommunity(communityId, email);
        return ResponseEntity.ok(ApiUtils.success(response));
    }

    @PostMapping("/{communityId}/like")
    public ResponseEntity<ApiResult<AddOrDeleteLikeRes>> addOrDeleteLike(
            @PathVariable Long communityId,
            @UserEmail String email
    ) {

        AddOrDeleteLikeRes response = apiCommunityService.addOrDeleteLike(communityId, email);
        return ResponseEntity.ok(ApiUtils.success(response));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResult<Slice<CommunitySearchDto.Response>>> searchCommunity(
            @Valid CommunitySearchDto.Request request,
            Optional<Integer> page,
            @UserEmail String email

    ) {

        Pageable pageable = PageRequest.of(
                page.isPresent() ? page.get() : 0,
                SET_PAGE_ITEM_MAX_COUNT,
                request.getOrderBy() == null ?
                        Sort.by(Sort.Direction.DESC, OrderBy.CREATE_TIME.name()) :
                        Sort.by(Sort.Direction.DESC, request.getOrderBy())
        );

        Slice<CommunitySearchDto.Response> response = apiCommunityService.searchCommunity(request.getSearchQuery(), pageable, email);
        return ResponseEntity.ok(ApiUtils.success(response));
    }

    @PostMapping("/{communityId}/save")
    public ResponseEntity<ApiResult<SaveOrRemoveCommunityRes>> saveOrRemoveCommunity(
            @PathVariable Long communityId,
            @UserEmail String email
    ) {

        SaveOrRemoveCommunityRes response = apiCommunityService.saveOrRemoveCommunity(communityId, email);
        return ResponseEntity.ok(ApiUtils.success(response));
    }

    @PostMapping("/{communityId}/report")
    public ResponseEntity<ApiResult<ReportCommunityRes>> reportCommunity(
            @PathVariable Long communityId,
            @UserEmail String email
    ) {
        ReportCommunityRes response = apiCommunityService.reportCommunity(communityId, email);
        return ResponseEntity.ok(ApiUtils.success(response));

    }


}
