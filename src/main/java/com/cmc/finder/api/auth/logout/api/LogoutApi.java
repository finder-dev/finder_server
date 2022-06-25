package com.cmc.finder.api.auth.logout.api;

import com.cmc.finder.api.auth.logout.dto.LogoutRequestDto;
import com.cmc.finder.api.auth.logout.service.LogoutService;
import com.cmc.finder.global.resolver.UserEmail;
import com.cmc.finder.global.response.ApiResult;
import com.cmc.finder.global.util.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LogoutApi {

    private final LogoutService logoutService;

    @PostMapping("/logout")
    public ResponseEntity<ApiResult<LogoutRequestDto>> logout(
            @UserEmail String email) {

        LogoutRequestDto logout = logoutService.logout(email);
        return ResponseEntity.ok(ApiUtils.success(logout));

    }

}
