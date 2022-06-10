package com.cmc.finder.api.auth.login.service;

import com.cmc.finder.domain.user.constant.UserType;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SocialLoginApiServiceFactory {

    private static Map<String, SocialLoginApiService> socialApiServices;

    public SocialLoginApiServiceFactory(Map<String, SocialLoginApiService> socialApiSerivces) {
        this.socialApiServices = socialApiSerivces;
    }

    public static SocialLoginApiService getSocialLoginApiService(UserType userType) {
        String socialApiServiceBeanName = "";

        if(UserType.KAKAO.equals(userType)) {
            socialApiServiceBeanName = "kakaoLoginApiServiceImpl";
        }

        if(UserType.APPLE.equals(userType)) {
            socialApiServiceBeanName = "appleLoginApiServiceImpl";
        }

        SocialLoginApiService socialLoginApiSerivce = socialApiServices.get(socialApiServiceBeanName);
        return socialLoginApiSerivce;
    }

}
