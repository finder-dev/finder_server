package com.cmc.finder.api.auth.login.api;

import com.cmc.finder.api.auth.login.dto.LoginDto;
import com.cmc.finder.api.auth.login.dto.OauthLoginDto;
import com.cmc.finder.api.auth.login.service.LoginService;
import com.cmc.finder.global.response.ApiResult;
import com.cmc.finder.global.util.ApiUtils;
import com.cmc.finder.global.validator.TokenValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LoginApi {

    private final LoginService loginService;
    private final TokenValidator tokenValidator;

    @PostMapping("/login")
    public ResponseEntity<ApiResult<LoginDto.Response>> login(
            @RequestBody @Valid LoginDto.Request request
    ) {

        LoginDto.Response response = loginService.login(request);
        return ResponseEntity.ok(ApiUtils.success(response));

    }

    @PostMapping("/oauth/login")
    public ResponseEntity<ApiResult<OauthLoginDto.Response>> socialLogin(
            @RequestBody @Valid OauthLoginDto.Request requestDto,
            HttpServletRequest request) {

        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        tokenValidator.validateAuthorization(authorization);
        tokenValidator.validateMemberType(requestDto.getUserType());

        String accessToken = authorization.split(" ")[1];

        OauthLoginDto.Response jwtTokenResponseDto = loginService.loginOauth(accessToken, requestDto);

        return ResponseEntity.ok(ApiUtils.success(jwtTokenResponseDto));

    }



}
