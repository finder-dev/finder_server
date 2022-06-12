package com.cmc.finder.api.auth.login.service;

import com.cmc.finder.api.auth.login.dto.LoginRequestDto;
import com.cmc.finder.api.auth.login.dto.OAuthAttributes;
import com.cmc.finder.api.auth.login.dto.OauthLoginDto;
import com.cmc.finder.api.auth.login.exception.LoginFailedException;
import com.cmc.finder.domain.jwt.entity.RefreshToken;
import com.cmc.finder.domain.jwt.dto.TokenDto;
import com.cmc.finder.domain.jwt.service.RefreshTokenRedisService;
import com.cmc.finder.domain.jwt.service.TokenManager;
import com.cmc.finder.domain.user.constant.UserType;
import com.cmc.finder.domain.user.entity.User;
import com.cmc.finder.domain.user.service.UserService;
import com.cmc.finder.domain.model.Email;
import com.cmc.finder.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class LoginService {

    private final TokenManager tokenManager;
    private final UserService userService;
    private final RefreshTokenRedisService refreshTokenRedisService;

    @Transactional
    public OauthLoginDto.Response loginOauth(String accessToken, OauthLoginDto.Request request) {

        OAuthAttributes oAuthAttributes = getSocialUserInfo(accessToken, UserType.from(request.getUserType()));

        User oauthUser;

        if (!userService.existsUser(Email.of(oAuthAttributes.getEmail()))) {

            oauthUser = oAuthAttributes.toUserEntity(request);
            userService.register(oauthUser);

        } else {
            oauthUser = userService.getUserByEmail(Email.of(oAuthAttributes.getEmail()));
        }

        TokenDto tokenDto = tokenManager.createTokenDto(oAuthAttributes.getEmail());
        saveRefreshToken(oauthUser, tokenDto);

        return OauthLoginDto.Response.of(tokenDto);
    }

    private OAuthAttributes getSocialUserInfo(String accessToken, UserType userType) {

        SocialLoginApiService socialLoginApiSerivce = SocialLoginApiServiceFactory.getSocialLoginApiService(userType);
        OAuthAttributes oAuthAttributes = socialLoginApiSerivce.getUserInfo(accessToken);
        return oAuthAttributes;

    }


    public TokenDto login(LoginRequestDto loginRequestDto) {

        User user = userService.getUserByEmail(Email.of(loginRequestDto.getEmail()));

        if (!user.getPassword().isMatches(loginRequestDto.getPassword())) {
            throw new LoginFailedException(ErrorCode.LOGIN_ERROR);
        }

        TokenDto tokenDto = tokenManager.createTokenDto(loginRequestDto.getEmail());
        saveRefreshToken(user, tokenDto);

        return tokenDto;
    }

    private void saveRefreshToken(User user, TokenDto tokenDto) {

        RefreshToken refreshToken = RefreshToken.of(
                user.getEmail().getValue(),
                tokenDto.getRefreshToken(),
                tokenDto.getRefreshTokenExpireTime()
        );
        refreshTokenRedisService.saveRefreshToken(refreshToken);
    }
}
