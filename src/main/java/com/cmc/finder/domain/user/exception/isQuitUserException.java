package com.cmc.finder.domain.user.exception;


import com.cmc.finder.global.error.exception.BusinessException;
import com.cmc.finder.global.error.exception.ErrorCode;

public class isQuitUserException extends BusinessException {

    public isQuitUserException(ErrorCode errorCode) {
        super(errorCode);
    }
}
