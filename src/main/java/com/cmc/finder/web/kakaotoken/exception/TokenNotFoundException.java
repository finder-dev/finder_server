package com.cmc.finder.web.kakaotoken.exception;

import com.cmc.finder.global.error.exception.BusinessException;
import com.cmc.finder.global.error.exception.ErrorCode;
import lombok.Getter;

@Getter
public class TokenNotFoundException extends BusinessException {

    public TokenNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }


}
