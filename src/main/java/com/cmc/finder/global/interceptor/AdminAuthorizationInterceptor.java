package com.cmc.finder.global.interceptor;

import com.cmc.finder.domain.jwt.service.TokenManager;
import com.cmc.finder.domain.user.constant.Role;
import com.cmc.finder.global.error.exception.ErrorCode;
import com.cmc.finder.global.error.exception.ForbiddenException;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdminAuthorizationInterceptor implements HandlerInterceptor {

    private final TokenManager tokenManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("AdminAuthorizationInterceptor preHandle");

        String authorization = request.getHeader("Authorization");

        String accessToken = authorization.split(" ")[1];

//        String role = tokenManager.getRole(accessToken);

        Claims tokenClaims = tokenManager.getTokenClaims(accessToken);
        String role = (String) tokenClaims.get("role");
        Role roleEnum = Role.valueOf(role);

//        if (!Role.ADMIN.equals(roleEnum)) {
//            throw new ForbiddenException(ErrorCode.FORBIDDEN_ADMIN);
//        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("AdminAuthorizationInterceptor postHandle");

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("AdminAuthorizationInterceptor afterCompletion");

    }
}
