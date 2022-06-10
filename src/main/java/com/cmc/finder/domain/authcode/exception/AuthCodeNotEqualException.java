package com.cmc.finder.domain.authcode.exception;

import com.cmc.finder.global.error.exception.EntityNotFoundException;
import com.cmc.finder.global.error.exception.ErrorCode;

public class AuthCodeNotEqualException extends EntityNotFoundException {

    public AuthCodeNotEqualException() {
        super(ErrorCode.AUTH_CODE_NOT_FOUND);
    }

    public AuthCodeNotEqualException(ErrorCode errorCode) {
        super(errorCode);
    }
}
