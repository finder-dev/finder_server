package com.cmc.finder.api.auth.signup.api;

import com.cmc.finder.api.auth.signup.dto.EmailSendDto;
import com.cmc.finder.api.auth.signup.dto.EmailAuthDto;
import com.cmc.finder.api.auth.signup.dto.NicknameCheckDto;
import com.cmc.finder.api.auth.signup.dto.SignUpDto;
import com.cmc.finder.api.auth.signup.service.SignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SignUpApi {

    private final SignUpService signUpService;

    @PostMapping("/signup")
    public ResponseEntity<SignUpDto.Response> signup(
            @Valid SignUpDto.Request signUpDto
    ) {

        SignUpDto.Response response = signUpService.signUpUser(signUpDto);

        return ResponseEntity.ok(response);

    }

    @PostMapping("/mail/send")
    public ResponseEntity<EmailSendDto.Response> sendEmail(
            @Valid EmailSendDto.Request request
    ) {

        EmailSendDto.Response response = signUpService.sendEmail(request.getEmail());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/mail/auth")
    public ResponseEntity<EmailAuthDto.Response> checkCode(
            @Valid EmailAuthDto.Request request
    ) {

        EmailAuthDto.Response response = signUpService.checkCode(request.getEmail(), request.getCode());
        return ResponseEntity.ok(response);
    }


    @GetMapping("/duplicated/nickname")
    public ResponseEntity<NicknameCheckDto.Response> checkNickname(
            @Valid NicknameCheckDto.Request request
    ) {
        return ResponseEntity.ok(signUpService.checkNickname(request));



    }
}
