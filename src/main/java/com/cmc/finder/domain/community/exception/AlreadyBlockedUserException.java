package com.cmc.finder.domain.community.exception;

import com.cmc.finder.global.error.exception.BusinessException;
import com.cmc.finder.global.error.exception.ErrorCode;

public class AlreadyBlockedUserException extends BusinessException {

    public AlreadyBlockedUserException(ErrorCode errorCode) {
        super(errorCode);
    }
}
