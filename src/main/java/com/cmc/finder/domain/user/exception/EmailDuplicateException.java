package com.cmc.finder.domain.user.exception;


import com.cmc.finder.global.error.exception.BusinessException;
import com.cmc.finder.global.error.exception.ErrorCode;

public class EmailDuplicateException extends BusinessException {

    public EmailDuplicateException() {
        super(ErrorCode.DUPLICATE_EMAIL);
    }
}
