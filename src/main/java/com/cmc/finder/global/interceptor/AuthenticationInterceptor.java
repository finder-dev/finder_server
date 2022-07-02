package com.cmc.finder.global.interceptor;

import com.cmc.finder.domain.jwt.constant.TokenType;
import com.cmc.finder.domain.jwt.service.TokenManager;
import com.cmc.finder.global.error.exception.AuthenticationException;
import com.cmc.finder.global.error.exception.ErrorCode;
import com.cmc.finder.global.validator.TokenValidator;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final TokenValidator tokenValidator;
    private final TokenManager tokenManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("AuthenticationInterceptor preHandle");

        // 정상적인 토큰인지 검증 -> 토큰 유무 확인, Bearer Grant Type 확인
        String authorization = request.getHeader("Authorization");
        tokenValidator.validateAuthorization(authorization);

        String accessToken = authorization.split(" ")[1];

        // 가져온 토큰이 해당 서버에서 발급한 JWT인지 검증
        if (!tokenManager.validateToken(accessToken)) {
            throw new AuthenticationException(ErrorCode.NOT_VALID_TOKEN);
        }

        // 토큰이 access token인지 검증
        Claims tokenClaims = tokenManager.getTokenClaims(accessToken);
        if (!TokenType.ACCESS.name().equals(tokenClaims.getSubject())) {
            throw new AuthenticationException(ErrorCode.NOT_ACCESS_TOKEN_TYPE);
        }

        // 액세스 토큰 만료 시간 검증
        if (tokenManager.isTokenExpired(tokenClaims.getExpiration())) {
            throw new AuthenticationException(ErrorCode.ACCESS_TOKEN_EXPIRED);
        }

        return true;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("AuthenticationInterceptor postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("AuthenticationInterceptor afterCompletion");

    }
}
