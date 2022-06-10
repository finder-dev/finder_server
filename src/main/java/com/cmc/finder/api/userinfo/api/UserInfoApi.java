package com.cmc.finder.api.userinfo.api;

import com.cmc.finder.api.userinfo.service.UserInfoService;
import com.cmc.finder.api.userinfo.dto.UpdateProfileDto;
import com.cmc.finder.global.resolver.UserEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserInfoApi {

    private final UserInfoService userInfoService;

    @PatchMapping("/profileImg")
    public ResponseEntity<UpdateProfileDto> updateProfile(
            @RequestPart("profileImg") MultipartFile profile,
            @UserEmail String email
    ){

        UpdateProfileDto updateProfileDto = userInfoService.updateProfileImg(profile, email);
        return ResponseEntity.ok(updateProfileDto);

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
