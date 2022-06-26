package com.cmc.finder.api.auth.login.service;

import com.cmc.finder.api.auth.login.dto.LoginRequestDto;
import com.cmc.finder.api.auth.login.dto.OAuthAttributes;
import com.cmc.finder.api.auth.login.dto.OauthLoginDto;
import com.cmc.finder.api.auth.login.exception.LoginFailedException;
import com.cmc.finder.domain.jwt.entity.RefreshToken;
import com.cmc.finder.domain.jwt.dto.TokenDto;
import com.cmc.finder.domain.jwt.service.RefreshTokenRedisService;
import com.cmc.finder.domain.jwt.service.TokenManager;
import com.cmc.finder.domain.model.MBTI;
import com.cmc.finder.domain.user.constant.UserType;
import com.cmc.finder.domain.user.entity.User;
import com.cmc.finder.domain.user.service.UserService;
import com.cmc.finder.domain.model.Email;
import com.cmc.finder.global.error.exception.ErrorCode;
import com.cmc.finder.infra.file.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class LoginService {

    @Value("${s3.users.path}")
    private String PATH;

    private final TokenManager tokenManager;
    private final UserService userService;
    private final S3Uploader s3Uploader;
    private final RefreshTokenRedisService refreshTokenRedisService;

    @Transactional
    public OauthLoginDto.Response loginOauth(String accessToken, OauthLoginDto.Request request) {

        OAuthAttributes oAuthAttributes = getSocialUserInfo(accessToken, UserType.from(request.getUserType()));

        User oauthUser;

        if (!userService.existsUser(Email.of(oAuthAttributes.getEmail()))) {

            //TODO 다시정리
            if (request.getMbti() == null) {
                throw new ValidationException("");
            }
            if (request.getNickname() == null) {
                throw new ValidationException("");
            }

            if (MBTI.isMBTI(request.getMbti())) {
                throw new ValidationException("");
            }

            String fileName = "";
            if (request.getProfileImg() != null) {
                fileName = s3Uploader.uploadFile(request.getProfileImg(), PATH);
            }

            oauthUser = oAuthAttributes.toUserEntity(request, fileName);
            userService.register(oauthUser);

        } else {
            oauthUser = userService.getUserByEmail(Email.of(oAuthAttributes.getEmail()));
            oauthUser.updateFcmToken(request.getFcmToken());
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


    @Transactional
    public TokenDto login(LoginRequestDto loginRequestDto) {

        User user = userService.getUserByEmail(Email.of(loginRequestDto.getEmail()));

        if (!user.getPassword().isMatches(loginRequestDto.getPassword())) {
            throw new LoginFailedException(ErrorCode.LOGIN_ERROR);
        }

        // fcm token update
        user.updateFcmToken(loginRequestDto.getFcmToken());

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
