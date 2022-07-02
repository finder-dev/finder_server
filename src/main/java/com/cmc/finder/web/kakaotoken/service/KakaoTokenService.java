package com.cmc.finder.web.kakaotoken.service;

import com.cmc.finder.global.error.exception.ErrorCode;
import com.cmc.finder.web.kakaotoken.config.KakaoTokenClient;
import com.cmc.finder.web.kakaotoken.dto.KakaoTokenResponseDto;
import com.cmc.finder.web.kakaotoken.exception.TokenNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class KakaoTokenService {

    @Value("${kakao.client.secret}")
    private String clientSecret;

    @Value("${kakao.client.id}")
    private String clientId;

    @Value("${kakao.redirect}")
    private String REDIRECT_URI;

    private final KakaoTokenClient kakaoTokenClient;

    private final String CONTENT_TYPE = "application/x-www-form-urlencoded;charset=utf-8";
    private final String GRANT_TYPE = "authorization_code";

    public KakaoTokenResponseDto getKakaoTokenInfo(String code) {

        ResponseEntity<KakaoTokenResponseDto> response = kakaoTokenClient.requestKakaoToken(CONTENT_TYPE,
                GRANT_TYPE, clientId, REDIRECT_URI, code, clientSecret);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new TokenNotFoundException(ErrorCode.TOKEN_NOT_FOUND);
        }
        return response.getBody();
    }
}
