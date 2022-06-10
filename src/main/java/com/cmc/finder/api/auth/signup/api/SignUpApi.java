package com.cmc.finder.api.auth.signup.api;

import com.cmc.finder.api.auth.signup.dto.EmailCheckDto;
import com.cmc.finder.api.auth.signup.dto.NicknameCheckDto;
import com.cmc.finder.api.auth.signup.dto.SignUpDto;
import com.cmc.finder.api.auth.signup.service.SignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SignUpApi {

    private final SignUpService signUpService;

    @PostMapping("/signup")
    public ResponseEntity<SignUpDto.Response> signup(
            @Valid SignUpDto.Request signUpDto,
            @RequestPart(value = "profileImg", required = false) MultipartFile profileImg
            ){

        SignUpDto.Response response = signUpService.signUpUser(signUpDto, profileImg);
        return ResponseEntity.ok(response);

    }

    @GetMapping("/duplicated/email/{email}")
    public ResponseEntity<EmailCheckDto> checkEmail(
            @PathVariable String email
    ){

        return ResponseEntity.ok(signUpService.emailCheck(email));

    }

    @GetMapping("/duplicated/nickname/{nickname}")
    public ResponseEntity<NicknameCheckDto> checkNickname(
            @PathVariable String nickname
    ){

        return ResponseEntity.ok(signUpService.nicknameCheck(nickname));

    }
}
