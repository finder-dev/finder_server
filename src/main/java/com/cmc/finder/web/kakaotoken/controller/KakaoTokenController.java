package com.cmc.finder.web.kakaotoken.controller;

import com.cmc.finder.web.kakaotoken.dto.KakaoTokenResponseDto;
import com.cmc.finder.web.kakaotoken.service.KakaoTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class KakaoTokenController {

    private final KakaoTokenService kakaoTokenService;

    @GetMapping("/login")
    public String login(){
        return "loginForm";
    }

    @GetMapping("/auth/kakao/callback")
    public @ResponseBody String loginCallback(String code){
        KakaoTokenResponseDto kakaoToken = kakaoTokenService.getKakaoTokenInfo(code);
        return "kakao token : " + kakaoToken;
    }

}