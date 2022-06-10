package com.cmc.finder.api.auth.token.service;

import com.cmc.finder.api.auth.token.dto.AccessTokenResponseDto;
import com.cmc.finder.domain.jwt.service.RefreshTokenRedisService;
import com.cmc.finder.domain.jwt.domain.RefreshToken;
import com.cmc.finder.domain.jwt.service.TokenManager;
import com.cmc.finder.global.error.exception.AuthenticationException;
import com.cmc.finder.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TokenService {

    private final TokenManager tokenManager;
    private final RefreshTokenRedisService refreshTokenRedisService;

    public AccessTokenResponseDto updateAccessToken(String token) {

        String email = tokenManager.getUserEmail(token);
        RefreshToken refreshToken = refreshTokenRedisService.getRefreshTokenByEmail(email);

        if (tokenManager.isTokenExpired(refreshToken.getExpiration())) {
            throw new AuthenticationException(ErrorCode.REFRESH_TOKEN_EXPIRED);
        }

        Date accessTokenExpireTime = tokenManager.createAccessTokenExpireTime();
        String accessToken = tokenManager.createAccessToken(email, accessTokenExpireTime);

        return AccessTokenResponseDto.of(accessToken, accessTokenExpireTime);

    }

}
