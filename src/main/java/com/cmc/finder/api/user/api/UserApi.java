package com.cmc.finder.api.user.api;

import com.cmc.finder.api.user.dto.BlockUserDto;
import com.cmc.finder.api.user.application.UserBlockService;
import com.cmc.finder.api.user.dto.*;
import com.cmc.finder.api.user.application.UserActivityService;
import com.cmc.finder.api.user.application.UserInfoService;
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
@RequestMapping("/api/users")
public class UserApi {

    @Value("${page.count}")
    private Integer SET_PAGE_ITEM_MAX_COUNT;

    private final UserInfoService userInfoService;
    private final UserActivityService userActivityService;
    private final UserBlockService userBlockService;

    @GetMapping
    public ResponseEntity<ApiResult<GetUserInfoRes>> getUserInfo(
            @UserEmail String email
    ) {

        GetUserInfoRes response = userInfoService.getUserInfo(email);
        return ResponseEntity.ok(ApiUtils.success(response));

    }

    @PatchMapping
    public ResponseEntity<ApiResult<UpdateUserDto.Response>> updateUser(
            @RequestBody @Valid UpdateUserDto.Request request,
            @UserEmail String email
    ) {

        UpdateUserDto.Response response = userInfoService.updateUser(request, email);
        return ResponseEntity.ok(ApiUtils.success(response));

    }

    @DeleteMapping
    public ResponseEntity<ApiResult<DeleteUserRes>> deleteUser(
            @UserEmail String email

    ) {
        DeleteUserRes response = userInfoService.deleteUser(email);
        return ResponseEntity.ok(ApiUtils.success(response));

    }


    @PatchMapping("/profileImg")
    public ResponseEntity<ApiResult<UpdateProfileImgDto.Response>> updateProfile(
            @Valid UpdateProfileImgDto.Request request,
            @UserEmail String email
    ) {

        UpdateProfileImgDto.Response response = userInfoService.updateProfileImg(request, email);
        return ResponseEntity.ok(ApiUtils.success(response));

    }

    @PatchMapping("/nickname")
    public ResponseEntity<ApiResult<UpdateNicknameDto.Response>> updateNickname(
            @Valid UpdateNicknameDto.Request request,
            @UserEmail String email
    ) {

        UpdateNicknameDto.Response response = userInfoService.updateNickname(request, email);
        return ResponseEntity.ok(ApiUtils.success(response));

    }

    @PatchMapping("/mbti")
    public ResponseEntity<ApiResult<UpdateMBTIDto.Response>> updateMBTI(
            @Valid UpdateMBTIDto.Request request,
            @UserEmail String email
    ) {

        UpdateMBTIDto.Response response = userInfoService.updateMBTI(request, email);
        return ResponseEntity.ok(ApiUtils.success(response));

    }

    @GetMapping("/activity/community")
    public ResponseEntity<ApiResult<Slice<GetUserActivityRes>>> getCommunityByUser(
            @UserEmail String email,
            Optional<Integer> page

    ) {

        Pageable pageable = PageRequest.of(
                page.isPresent() ? page.get() : 0,
                SET_PAGE_ITEM_MAX_COUNT
        );

        Slice<GetUserActivityRes> response = userActivityService.getCommunityByUser(email, pageable);
        return ResponseEntity.ok(ApiUtils.success(response));

    }

    @GetMapping("/activity/answer")
    public ResponseEntity<ApiResult<Slice<GetUserActivityRes>>> getCommunityByCommentUser(
            @UserEmail String email,
            Optional<Integer> page

    ) {

        Pageable pageable = PageRequest.of(
                page.isPresent() ? page.get() : 0,
                SET_PAGE_ITEM_MAX_COUNT
        );

        Slice<GetUserActivityRes> response = userActivityService.getCommunityByCommentUser(email, pageable);
        return ResponseEntity.ok(ApiUtils.success(response));

    }

    @GetMapping("/activity/notification")
    public ResponseEntity<ApiResult<Slice<GetNotificationRes>>> getNotification(
            @UserEmail String email,
            Optional<Integer> page

    ) {

        Pageable pageable = PageRequest.of(
                page.isPresent() ? page.get() : 0,
                SET_PAGE_ITEM_MAX_COUNT
        );

        Slice<GetNotificationRes> response = userActivityService.getNotification(email, pageable);
        return ResponseEntity.ok(ApiUtils.success(response));

    }

    @GetMapping("/save")
    public ResponseEntity<ApiResult<Slice<GetUserActivityRes>>> getSaveCommunity(
            @UserEmail String email,
            Optional<Integer> page

    ) {
        Pageable pageable = PageRequest.of(
                page.isPresent() ? page.get() : 0,
                SET_PAGE_ITEM_MAX_COUNT
        );

        Slice<GetUserActivityRes> response = userActivityService.getSaveCommunity(email, pageable);
        return ResponseEntity.ok(ApiUtils.success(response));
    }


    @GetMapping("/activity/message")
    public ResponseEntity<ApiResult<Slice<GetMessageRes>>> getMessagesByFromUser(
            @UserEmail String email,
            Optional<Integer> page
    ) {

        Pageable pageable = PageRequest.of(
                page.isPresent() ? page.get() : 0,
                SET_PAGE_ITEM_MAX_COUNT
        );

        Slice<GetMessageRes> response = userActivityService.getMessageByOwner(email, pageable);

        return ResponseEntity.ok(ApiUtils.success(response));

    }

    @PatchMapping("/notification")
    public ResponseEntity<ApiResult<NotificationOnOffRes>> onOffNotification(
            @UserEmail String email

    ){
        NotificationOnOffRes response = userInfoService.onOffNotificaiton(email);
        return ResponseEntity.ok(ApiUtils.success(response));

    }

    @GetMapping("/notification")
    public ResponseEntity<ApiResult<GetNotificationActiveRes>> getNotificaitonActive(
            @UserEmail String email
    ){

        GetNotificationActiveRes response = userInfoService.getNotificaitonActive(email);
        return ResponseEntity.ok(ApiUtils.success(response));

    }

    @PostMapping("/block")
    public ResponseEntity<ApiResult<BlockUserDto.Response>> blockUser(
            @RequestBody @Valid BlockUserDto.Request request,
            @UserEmail String email
    ) {
        BlockUserDto.Response response = userBlockService.blockUser(request, email);
        return ResponseEntity.ok(ApiUtils.success(response));
    }


}
