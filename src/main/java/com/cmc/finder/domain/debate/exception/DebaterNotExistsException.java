package com.cmc.finder.domain.debate.exception;

import com.cmc.finder.global.error.exception.BusinessException;
import com.cmc.finder.global.error.exception.ErrorCode;

public class DebaterNotExistsException extends BusinessException {

    public DebaterNotExistsException(String message) {
        super(message);
    }

    public DebaterNotExistsException(ErrorCode errorCode) {
        super(errorCode);
    }
}
