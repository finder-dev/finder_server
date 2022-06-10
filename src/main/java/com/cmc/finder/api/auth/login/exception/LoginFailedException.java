package com.cmc.finder.api.auth.login.exception;

import com.cmc.finder.global.error.exception.BusinessException;
import com.cmc.finder.global.error.exception.ErrorCode;

public class LoginFailedException extends BusinessException {

    public LoginFailedException(String message) {
        super(message);
    }

    public LoginFailedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
