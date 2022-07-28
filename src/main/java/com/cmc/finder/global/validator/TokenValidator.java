package com.cmc.finder.global.validator;

import com.cmc.finder.domain.jwt.constant.GrantType;
import com.cmc.finder.domain.user.constant.UserType;
import com.cmc.finder.global.error.exception.InvalidValueException;
import com.cmc.finder.global.error.exception.AuthenticationException;
import com.cmc.finder.global.error.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class TokenValidator {

    public void validateAuthorization(String authorizationHeader) {

        // 1. 토큰 유무 확인
        if(!StringUtils.hasText(authorizationHeader)) {
            throw new AuthenticationException(ErrorCode.NOT_EXISTS_AUTHORIZATION);
        }

        // 2. Bearer Grant Type 확인
        String[] authorizations = authorizationHeader.split(" ");
        if(authorizations.length < 2 || (!GrantType.BEARRER.getType().equals(authorizations[0]))) {
            throw new AuthenticationException(ErrorCode.NOT_VALID_BEARER_GRANT_TYPE);
        }
    }

    public void validateMemberType(String memberType) {
        if(!UserType.isUserType(memberType)) {
            throw new InvalidValueException(ErrorCode.INVALID_USER_TYPE);
        }
    }

//    public void validateRefreshTokenExpirationTime(LocalDateTime refreshTokenExpirationTime, LocalDateTime now) {
//        if(refreshTokenExpirationTime.isBefore(now)) {
//            throw new AuthenticationException(ErrorCode.REFRESH_TOKEN_EXPIRED);
//        }
//    }
}
