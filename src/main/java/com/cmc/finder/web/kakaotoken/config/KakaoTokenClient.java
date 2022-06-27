package com.cmc.finder.web.kakaotoken.config;

import com.cmc.finder.web.kakaotoken.dto.KakaoTokenResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "kakaoTokenClient", url = "https://kauth.kakao.com")
public interface KakaoTokenClient {
    @PostMapping(value = "/oauth/token", consumes = "application/json")
    ResponseEntity<KakaoTokenResponseDto> requestKakaoToken(
            @RequestHeader("Content-Type") String contentType,
            @RequestParam("grant_type") String grantType,
            @RequestParam("client_id") String clientId,
            @RequestParam("redirect_uri") String redirectUri,
            @RequestParam("code") String code,
            @RequestParam("client_secret") String clientSecret
    );

}
