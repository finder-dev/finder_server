package com.cmc.finder.api.auth.login.service.kakao;

import com.cmc.finder.api.auth.login.service.SocialLoginApiService;
import com.cmc.finder.api.auth.login.dto.OAuthAttributes;
import com.cmc.finder.domain.jwt.constant.GrantType;
import com.cmc.finder.domain.user.constant.UserType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KakaoLoginApiServiceImpl implements SocialLoginApiService {

    private final KakaoFeignClient kakaoFeignClient;
    private final String CONTENT_TYPE = "application/x-www-form-urlencoded;charset=utf-8";

    @Override
    public OAuthAttributes getUserInfo(String accessToken) {

        KakaoUserInfo kakaoUserInfo  = kakaoFeignClient.getKakaoUserInfo(CONTENT_TYPE, GrantType.BEARRER.getType() + " " + accessToken);
        String email = kakaoUserInfo.getKakaoAccount().getEmail();
//        String nickname = kakaoUserInfo.getKakaoAccount().getProfile().getNickname(); // 실시간 프로필에 있는 닉네임 조회

        return OAuthAttributes.builder()

                .email(StringUtils.hasText(email) ? email : kakaoUserInfo.getId() )
                .userType(UserType.KAKAO)
                .build();

    }



}
