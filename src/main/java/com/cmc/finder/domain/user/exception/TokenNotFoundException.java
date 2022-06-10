package com.cmc.finder.domain.user.exception;


import com.cmc.finder.global.error.exception.BusinessException;
import com.cmc.finder.global.error.exception.ErrorCode;

public class TokenNotFoundException extends BusinessException {

    public TokenNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

}