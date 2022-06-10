package com.cmc.finder.domain.authcode.exception;

import com.cmc.finder.global.error.exception.EntityNotFoundException;
import com.cmc.finder.global.error.exception.ErrorCode;

public class AuthCodeNotFoundException extends EntityNotFoundException {

    public AuthCodeNotFoundException() {
        super(ErrorCode.AUTH_CODE_NOT_FOUND);
    }

    public AuthCodeNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
