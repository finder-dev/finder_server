package com.cmc.finder.api.community.api;

import com.cmc.finder.api.community.dto.CommunityHotDto;
import com.cmc.finder.api.community.service.ApiCommunityService;
import com.cmc.finder.global.response.ApiResult;
import com.cmc.finder.global.util.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class CommunityApi {

    private final ApiCommunityService apiCommunityService;

    @GetMapping("/hot")
    public ResponseEntity<ApiResult<List<CommunityHotDto>>> getDebates(
    ) {

        List<CommunityHotDto> communityHotDtos = apiCommunityService.getHotCommunity();
        return ResponseEntity.ok(ApiUtils.success(communityHotDtos));

    }

}
