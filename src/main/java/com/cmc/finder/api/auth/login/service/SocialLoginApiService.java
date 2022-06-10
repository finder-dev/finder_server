package com.cmc.finder.api.auth.login.service;


import com.cmc.finder.api.auth.login.dto.OAuthAttributes;

public interface SocialLoginApiService {

    OAuthAttributes getUserInfo(String accessToken);
}
