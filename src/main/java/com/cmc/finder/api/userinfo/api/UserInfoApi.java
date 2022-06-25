package com.cmc.finder.api.userinfo.api;

import com.cmc.finder.api.userinfo.dto.UpdateMBTIDto;
import com.cmc.finder.api.userinfo.dto.UpdateNicknameDto;
import com.cmc.finder.api.userinfo.service.UserInfoService;
import com.cmc.finder.api.userinfo.dto.UpdateProfileImgDto;
import com.cmc.finder.global.resolver.UserEmail;
import com.cmc.finder.global.response.ApiResult;
import com.cmc.finder.global.util.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserInfoApi {

    private final UserInfoService userInfoService;

    @PatchMapping("/profileImg")
    public ResponseEntity<ApiResult<UpdateProfileImgDto.Response>> updateProfile(
            @Valid UpdateProfileImgDto.Request request,
            @UserEmail String email
    ){

        UpdateProfileImgDto.Response response = userInfoService.updateProfileImg(request, email);
        return ResponseEntity.ok(ApiUtils.success(response));

    }

    @PatchMapping("/nickname")
    public ResponseEntity<ApiResult<UpdateNicknameDto.Response>> updateNickname(
            @Valid UpdateNicknameDto.Request request,
            @UserEmail String email
    ){

        UpdateNicknameDto.Response response = userInfoService.updateNickname(request, email);
        return ResponseEntity.ok(ApiUtils.success(response));

    }


    @PatchMapping("/mbti")
    public ResponseEntity<ApiResult<UpdateMBTIDto.Response>> updateMBTI(
            @Valid UpdateMBTIDto.Request request,
            @UserEmail String email
    ){

        UpdateMBTIDto.Response response = userInfoService.updateMBTI(request, email);
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
