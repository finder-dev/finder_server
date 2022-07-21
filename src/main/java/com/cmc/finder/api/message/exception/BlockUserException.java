package com.cmc.finder.api.message.exception;

import com.cmc.finder.global.error.exception.BusinessException;
import com.cmc.finder.global.error.exception.ErrorCode;

public class BlockUserException extends BusinessException {

    public BlockUserException(ErrorCode errorCode) {
        super(errorCode);
    }
}
