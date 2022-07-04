package com.cmc.finder.api.auth.token.api;

import com.cmc.finder.api.auth.token.dto.AccessTokenResponse;
import com.cmc.finder.api.auth.token.service.TokenService;
import com.cmc.finder.global.response.ApiResult;
import com.cmc.finder.global.util.ApiUtils;
import com.cmc.finder.global.validator.TokenValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/token")
public class TokenApi {

    private final TokenService tokenService;
    private final TokenValidator tokenValidator;

    @PostMapping("/reissue")
    public ResponseEntity<ApiResult<AccessTokenResponse>> updateAccessToken(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization
    ) {

        tokenValidator.validateAuthorization(authorization);

        String refreshToken = authorization.split(" ")[1];
        AccessTokenResponse accessTokenResponse = tokenService.reIssueAccessToken(refreshToken);

        return ResponseEntity.ok(ApiUtils.success(accessTokenResponse));
    }

}
