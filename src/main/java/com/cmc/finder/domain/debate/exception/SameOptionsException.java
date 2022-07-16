package com.cmc.finder.domain.debate.exception;

import com.cmc.finder.global.error.exception.BusinessException;
import com.cmc.finder.global.error.exception.ErrorCode;

public class SameOptionsException extends BusinessException {

    public SameOptionsException(ErrorCode errorCode) {
        super(errorCode);
    }
}
