package com.cmc.finder.api.auth.logout.service;

import com.cmc.finder.api.auth.logout.dto.LogoutRes;
import com.cmc.finder.domain.jwt.application.RefreshTokenRedisService;
import com.cmc.finder.domain.user.application.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class LogoutService {

    private final UserService userService;
    private final RefreshTokenRedisService refreshTokenRedisService;

    @Transactional
    public LogoutRes logout(String email) {

        refreshTokenRedisService.deleteRefreshToken(email);
        return LogoutRes.of();

    }
}
