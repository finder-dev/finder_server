package com.cmc.finder.infra.email.exception;

import com.cmc.finder.global.error.exception.BusinessException;
import com.cmc.finder.global.error.exception.ErrorCode;

public class SendFailEmailException extends BusinessException {

    public SendFailEmailException() {
        super(ErrorCode.FAILED_TO_SEND_MAIL);
    }
}
