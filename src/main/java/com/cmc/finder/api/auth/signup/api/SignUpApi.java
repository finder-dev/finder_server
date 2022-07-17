package com.cmc.finder.api.auth.signup.api;

import com.cmc.finder.api.auth.signup.dto.EmailSendDto;
import com.cmc.finder.api.auth.signup.dto.EmailAuthDto;
import com.cmc.finder.api.auth.signup.dto.NicknameCheckDto;
import com.cmc.finder.api.auth.signup.dto.SignUpDto;
import com.cmc.finder.api.auth.signup.service.SignUpService;
import com.cmc.finder.global.response.ApiResult;
import com.cmc.finder.global.util.ApiUtils;
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
    public ResponseEntity<ApiResult<SignUpDto.Response>> signup(
            @Valid SignUpDto.Request signUpDto
    ) {

        SignUpDto.Response response = signUpService.signUpUser(signUpDto);

        return ResponseEntity.ok(ApiUtils.success(response));

    }

    //TODO json으로 받도록 변경

    @PostMapping("/mail/send")
    public ResponseEntity<ApiResult<EmailSendDto.Response>> sendEmail(
            @Valid EmailSendDto.Request request
    ) {

        EmailSendDto.Response response = signUpService.sendEmail(request.getEmail());
        return ResponseEntity.ok(ApiUtils.success(response));
    }

    @PostMapping("/mail/auth")
    public ResponseEntity<ApiResult<EmailAuthDto.Response>> checkCode(
            @Valid EmailAuthDto.Request request
    ) {

        EmailAuthDto.Response response = signUpService.checkCode(request.getEmail(), request.getCode());
        return ResponseEntity.ok(ApiUtils.success(response));
    }


    @GetMapping("/duplicated/nickname")
    public ResponseEntity<ApiResult<NicknameCheckDto.Response>> checkNickname(
            @Valid NicknameCheckDto.Request request
    ) {

        return ResponseEntity.ok(ApiUtils.success(signUpService.checkNickname(request)));
    }
}
