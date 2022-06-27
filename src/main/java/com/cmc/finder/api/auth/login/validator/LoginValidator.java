package com.cmc.finder.api.auth.login.validator;

import com.cmc.finder.api.auth.login.dto.OauthLoginDto;
import com.cmc.finder.api.auth.login.exception.LoginFailedException;
import com.cmc.finder.domain.model.MBTI;
import com.cmc.finder.domain.user.constant.UserType;
import com.cmc.finder.domain.user.entity.User;
import com.cmc.finder.global.config.InvalidValueException;
import com.cmc.finder.global.error.exception.ErrorCode;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;

@Service
public class LoginValidator {

    public void validateOauthSignUpRequest(OauthLoginDto.Request request) {

        if (request.getMbti() == null) {
            throw new ValidationException("MBTI는 필수값 입니다.");
        }
        if (request.getNickname() == null) {
            throw new ValidationException("닉네임은 필수값 입니다.");
        }
        if (!MBTI.isMBTI(request.getMbti())) {
            throw new InvalidValueException(ErrorCode.INVALID_MBTI_TYPE);
        }
    }

    public void validateUserType(User user, UserType userType) {

        if (user.getUserType() != userType) {
            throw new LoginFailedException(ErrorCode.NOT_MATCH_USER_TYPE);
        }

    }

    public void validatePassword(User user, String password) {
        if (!user.getPassword().isMatches(password)) {
            throw new LoginFailedException(ErrorCode.LOGIN_ERROR);
        }
    }
}
