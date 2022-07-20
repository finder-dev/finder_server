package com.cmc.finder.domain.debate.exception;

import com.cmc.finder.global.error.exception.BusinessException;
import com.cmc.finder.global.error.exception.ErrorCode;

public class ClosedDebateException extends BusinessException {

    public ClosedDebateException(ErrorCode errorCode) {
        super(errorCode);
    }
}
