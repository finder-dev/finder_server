package com.cmc.finder.api.auth.logout.service;

import com.cmc.finder.api.auth.logout.dto.LogoutResponse;
import com.cmc.finder.domain.jwt.application.RefreshTokenRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class LogoutService {

    private final RefreshTokenRedisService refreshTokenRedisService;

    @Transactional
    public LogoutResponse logout(String email) {

        refreshTokenRedisService.deleteRefreshToken(email);
        return LogoutResponse.of();

    }
}
