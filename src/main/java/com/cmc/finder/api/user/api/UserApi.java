package com.cmc.finder.api.user.api;

import com.cmc.finder.api.user.dto.MBTIUpdateDto;
import com.cmc.finder.api.user.dto.NicknameUpdateDto;
import com.cmc.finder.api.user.application.UserActivityService;
import com.cmc.finder.api.user.application.UserInfoService;
import com.cmc.finder.api.user.dto.ProfileImgUpdateDto;
import com.cmc.finder.api.user.dto.UserActivityResponse;
import com.cmc.finder.global.resolver.UserEmail;
import com.cmc.finder.global.response.ApiResult;
import com.cmc.finder.global.util.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserApi {

    private final UserInfoService userInfoService;
    private final UserActivityService userActivityService;

    @PatchMapping("/profileImg")
    public ResponseEntity<ApiResult<ProfileImgUpdateDto.Response>> updateProfile(
            @Valid ProfileImgUpdateDto.Request request,
            @UserEmail String email
    ){

        ProfileImgUpdateDto.Response response = userInfoService.updateProfileImg(request, email);
        return ResponseEntity.ok(ApiUtils.success(response));

    }

    @PatchMapping("/nickname")
    public ResponseEntity<ApiResult<NicknameUpdateDto.Response>> updateNickname(
            @Valid NicknameUpdateDto.Request request,
            @UserEmail String email
    ){

        NicknameUpdateDto.Response response = userInfoService.updateNickname(request, email);
        return ResponseEntity.ok(ApiUtils.success(response));

    }


    @PatchMapping("/mbti")
    public ResponseEntity<ApiResult<MBTIUpdateDto.Response>> updateMBTI(
            @Valid MBTIUpdateDto.Request request,
            @UserEmail String email
    ){

        MBTIUpdateDto.Response response = userInfoService.updateMBTI(request, email);
        return ResponseEntity.ok(ApiUtils.success(response));

    }

    @GetMapping("/activity")
    public ResponseEntity<ApiResult<List<UserActivityResponse>>> getUserActivity(
            @UserEmail String email
    ){

        List<UserActivityResponse> response = userActivityService.getUserActivity(email);
        return ResponseEntity.ok(ApiUtils.success(response));

    }


//    @GetMapping
//    public ResponseEntity<UserDto> getUser(
//            @RequestParam("email") String email
//    ) {
//
//        User user = userService.getUserByEmail(Email.of(email));
//        List<Keyword> keywordByMember = keywordService.getKeywordByMember(user);
//
//        return ResponseEntity.ok(UserDto.of(user, keywordByMember));
//
//    }

}
