package com.cmc.finder.api.auth.signup.api;

import com.cmc.finder.api.auth.signup.dto.EmailSendDto;
import com.cmc.finder.api.auth.signup.dto.EmailValidationDto;
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
            @Valid SignUpDto.Request signUpDto
    ){

        SignUpDto.Response response = signUpService.signUpUser(signUpDto);
        return ResponseEntity.ok(response);

    }

    @PostMapping("/mail/send")
    public ResponseEntity<EmailSendDto> sendEmail(@RequestPart("email") String email) {

        EmailSendDto emailSendDto = signUpService.sendEmail(email);
        return ResponseEntity.ok(emailSendDto);
    }

    @PostMapping("/mail/auth")
    public ResponseEntity<EmailValidationDto> checkCode(
            @RequestPart("email") String email,
            @RequestPart("code") String code) {

        EmailValidationDto emailValidationDto = signUpService.checkCode(email, code);
        return ResponseEntity.ok(emailValidationDto);
    }


    @GetMapping("/duplicated/nickname/{nickname}")
    public ResponseEntity<NicknameCheckDto> checkNickname(
            @PathVariable String nickname
    ){

        return ResponseEntity.ok(signUpService.checkNickname(nickname));

    }
}
