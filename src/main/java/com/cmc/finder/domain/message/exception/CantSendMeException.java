package com.cmc.finder.domain.message.exception;

import com.cmc.finder.global.error.exception.BusinessException;
import com.cmc.finder.global.error.exception.ErrorCode;

public class CantSendMeException extends BusinessException {

    public CantSendMeException(ErrorCode errorCode) {
        super(errorCode);
    }
}
