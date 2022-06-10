package com.cmc.finder.api.auth.login.api;

import com.cmc.finder.api.auth.login.dto.LoginRequestDto;
import com.cmc.finder.api.auth.login.dto.OauthLoginDto;
import com.cmc.finder.api.auth.login.service.LoginService;
import com.cmc.finder.domain.jwt.dto.TokenDto;
import com.cmc.finder.global.validator.TokenValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LoginApi {

    private final LoginService loginService;
    private final TokenValidator tokenValidator;

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(
            @RequestBody LoginRequestDto loginRequestDto
    ) {

        TokenDto tokenDto = loginService.login(loginRequestDto);
        return ResponseEntity.ok(tokenDto);

    }

    @PostMapping("/oauth/login")
    public ResponseEntity<OauthLoginDto.Response> socialLogin(
            @RequestBody OauthLoginDto.Request requestDto,
            HttpServletRequest request) {

        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        tokenValidator.validateAuthorization(authorization);
        tokenValidator.validateMemberType(requestDto.getUserType());

        String accessToken = authorization.split(" ")[1];

        OauthLoginDto.Response jwtTokenResponseDto = loginService.loginOauth(accessToken, requestDto);

        return ResponseEntity.ok(jwtTokenResponseDto);

    }



}
