package com.cmc.finder.api.auth.signup.api;

import com.cmc.finder.api.auth.signup.dto.EmailSendDto;
import com.cmc.finder.api.auth.signup.dto.EmailValidationDto;
import com.cmc.finder.api.auth.signup.dto.NicknameCheckDto;
import com.cmc.finder.api.auth.signup.dto.SignUpDto;
import com.cmc.finder.api.auth.signup.service.SignUpService;
import com.cmc.finder.api.qna.qustion.dto.QuestionSimpleDto;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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


    @GetMapping("/duplicated/nickname")
    public ResponseEntity<NicknameCheckDto.Response> checkNickname(
            @Valid NicknameCheckDto.Request request
    ){
        return ResponseEntity.ok(signUpService.checkNickname(request));

    }
}
