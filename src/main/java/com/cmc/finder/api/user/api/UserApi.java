package com.cmc.finder.api.user.api;

import com.cmc.finder.api.user.dto.*;
import com.cmc.finder.api.user.application.UserActivityService;
import com.cmc.finder.api.user.application.UserInfoService;
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
@RequestMapping("/api/users")
public class UserApi {

    @Value("${page.count}")
    private Integer SET_PAGE_ITEM_MAX_COUNT;

    private final UserInfoService userInfoService;
    private final UserActivityService userActivityService;

    @GetMapping
    public ResponseEntity<ApiResult<GetUserInfoRes>> getUserInfo(
            @UserEmail String email
    ) {

        GetUserInfoRes response = userInfoService.getUserInfo(email);
        return ResponseEntity.ok(ApiUtils.success(response));

    }

    @PatchMapping("/profileImg")
    public ResponseEntity<ApiResult<ProfileImgUpdateDto.Response>> updateProfile(
            @Valid ProfileImgUpdateDto.Request request,
            @UserEmail String email
    ) {

        ProfileImgUpdateDto.Response response = userInfoService.updateProfileImg(request, email);
        return ResponseEntity.ok(ApiUtils.success(response));

    }

    @PatchMapping("/nickname")
    public ResponseEntity<ApiResult<NicknameUpdateDto.Response>> updateNickname(
            @Valid NicknameUpdateDto.Request request,
            @UserEmail String email
    ) {

        NicknameUpdateDto.Response response = userInfoService.updateNickname(request, email);
        return ResponseEntity.ok(ApiUtils.success(response));

    }


    @PatchMapping("/mbti")
    public ResponseEntity<ApiResult<MBTIUpdateDto.Response>> updateMBTI(
            @Valid MBTIUpdateDto.Request request,
            @UserEmail String email
    ) {

        MBTIUpdateDto.Response response = userInfoService.updateMBTI(request, email);
        return ResponseEntity.ok(ApiUtils.success(response));

    }

    @GetMapping("/activity/community")
    public ResponseEntity<ApiResult<Page<GetUserActivityRes>>> getCommunityByUser(
            @UserEmail String email,
            Optional<Integer> page

    ) {

        Pageable pageable = PageRequest.of(
                page.isPresent() ? page.get() : 0,
                SET_PAGE_ITEM_MAX_COUNT
        );

        Page<GetUserActivityRes> response = userActivityService.getCommunityByUser(email, pageable);
        return ResponseEntity.ok(ApiUtils.success(response));

    }

    @GetMapping("/activity/answer")
    public ResponseEntity<ApiResult<Page<GetUserActivityRes>>> getCommunityByCommentUser(
            @UserEmail String email,
            Optional<Integer> page

    ) {

        Pageable pageable = PageRequest.of(
                page.isPresent() ? page.get() : 0,
                SET_PAGE_ITEM_MAX_COUNT
        );

        Page<GetUserActivityRes> response = userActivityService.getCommunityByCommentUser(email, pageable);
        return ResponseEntity.ok(ApiUtils.success(response));

    }

    @GetMapping("/activity/notification")
    public ResponseEntity<ApiResult<Page<GetNotificationRes>>> getNotification(
            @UserEmail String email,
            Optional<Integer> page

    ) {

        Pageable pageable = PageRequest.of(
                page.isPresent() ? page.get() : 0,
                SET_PAGE_ITEM_MAX_COUNT
        );

        Page<GetNotificationRes> response = userActivityService.getNotification(email, pageable);
        return ResponseEntity.ok(ApiUtils.success(response));

    }

    @GetMapping("/save")
    public ResponseEntity<ApiResult<Page<GetSaveCommunityRes>>> getSaveCommunity(
            @UserEmail String email,
            Optional<Integer> page

    ) {

        Pageable pageable = PageRequest.of(
                page.isPresent() ? page.get() : 0,
                SET_PAGE_ITEM_MAX_COUNT
        );

        Page<GetSaveCommunityRes> response = userActivityService.getSaveCommunity(email, pageable);
        return ResponseEntity.ok(ApiUtils.success(response));
    }

//    @GetMapping("/notification")
//    public ResponseEntity<ApiResult<Page<GetSaveCommunityRes>>> getNotification(
//            @UserEmail String email,
//            Optional<Integer> page
//
//    ) {
//
//        Pageable pageable = PageRequest.of(
//                page.isPresent() ? page.get() : 0,
//                SET_PAGE_ITEM_MAX_COUNT
//        );
//
//        Page<GetSaveCommunityRes> response = userActivityService.getSaveCommunity(email, pageable);
//        return ResponseEntity.ok(ApiUtils.success(response));
//    }


}
