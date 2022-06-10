package com.cmc.finder.domain.user.exception;

import com.cmc.finder.global.error.exception.BusinessException;
import com.cmc.finder.global.error.exception.ErrorCode;

public class NicknameDuplicateException extends BusinessException {

    public NicknameDuplicateException() {
        super(ErrorCode.DUPLICATE_NICKNAME);
    }
}
