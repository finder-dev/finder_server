package com.cmc.finder.api.auth.login.service;

import com.cmc.finder.api.auth.login.dto.LoginDto;
import com.cmc.finder.api.auth.login.dto.OAuthAttributes;
import com.cmc.finder.api.auth.login.dto.OauthLoginDto;
import com.cmc.finder.api.auth.login.validator.LoginValidator;
import com.cmc.finder.domain.jwt.entity.RefreshToken;
import com.cmc.finder.domain.jwt.dto.TokenDto;
import com.cmc.finder.domain.jwt.application.RefreshTokenRedisService;
import com.cmc.finder.domain.jwt.application.TokenManager;
import com.cmc.finder.domain.user.constant.UserType;
import com.cmc.finder.domain.user.entity.User;
import com.cmc.finder.domain.user.application.UserService;
import com.cmc.finder.domain.model.Email;
import com.cmc.finder.global.error.exception.BusinessException;
import com.cmc.finder.global.error.exception.ErrorCode;
import com.cmc.finder.infra.file.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class LoginService {

    @Value("${s3.users.path}")
    private String PATH;

    private final TokenManager tokenManager;
    private final UserService userService;
    private final S3Uploader s3Uploader;
    private final LoginValidator loginValidator;
    private final RefreshTokenRedisService refreshTokenRedisService;

    @Transactional
    public OauthLoginDto.Response loginOauth(String accessToken, OauthLoginDto.Request request) {

        OAuthAttributes oAuthAttributes = getSocialUserInfo(accessToken, UserType.from(request.getUserType()));
        User oauthUser;

        // 회원가입
        if (!userService.existsUser(Email.of(oAuthAttributes.getEmail()))) {

            if (request.getNickname() == null && request.getMbti() == null) {
                throw new BusinessException(ErrorCode.PROCEED_WITH_SIGNUP);
            }

            // 회원가입
            else {
                String fileName = "";
                if (request.getProfileImg() != null) {
                    fileName = s3Uploader.uploadFile(request.getProfileImg(), PATH);
                }

                loginValidator.validateOauthSignUpRequest(request);
                oauthUser = oAuthAttributes.toEntity(request, fileName);
                userService.register(oauthUser);

            }


        } else {

            oauthUser = userService.getUserByEmail(Email.of(oAuthAttributes.getEmail()));
            loginValidator.validateUserType(oauthUser, UserType.from(request.getUserType()));

            oauthUser.updateFcmToken(request.getFcmToken());
        }

        TokenDto tokenDto = tokenManager.createTokenDto(oAuthAttributes.getEmail());
        saveRefreshToken(oauthUser, tokenDto);

        return OauthLoginDto.Response.from(tokenDto);
    }

    private OAuthAttributes getSocialUserInfo(String accessToken, UserType userType) {

        SocialLoginApiService socialLoginApiSerivce = SocialLoginApiServiceFactory.getSocialLoginApiService(userType);
        OAuthAttributes oAuthAttributes = socialLoginApiSerivce.getUserInfo(accessToken);
        return oAuthAttributes;

    }


    @Transactional
    public LoginDto.Response login(LoginDto.Request request) {

        User user = userService.getUserByEmail(Email.of(request.getEmail()));

        loginValidator.validateUserType(user, UserType.GENERAL);
        loginValidator.validatePassword(user, request.getPassword());

        // fcm token update
        user.updateFcmToken(request.getFcmToken());

        TokenDto tokenDto = tokenManager.createTokenDto(request.getEmail());
        saveRefreshToken(user, tokenDto);

        return LoginDto.Response.from(tokenDto);
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
