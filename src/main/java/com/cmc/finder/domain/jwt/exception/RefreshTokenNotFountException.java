package com.cmc.finder.domain.jwt.exception;

import com.cmc.finder.global.error.exception.EntityNotFoundException;
import com.cmc.finder.global.error.exception.ErrorCode;

import java.util.function.Supplier;

public class RefreshTokenNotFountException extends EntityNotFoundException {

    public RefreshTokenNotFountException() {
        super(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
    }

    public RefreshTokenNotFountException(ErrorCode errorCode) {
        super(errorCode);
    }
}
