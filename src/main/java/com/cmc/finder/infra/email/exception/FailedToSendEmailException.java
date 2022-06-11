package com.cmc.finder.infra.email.exception;

import com.cmc.finder.global.error.exception.BusinessException;
import com.cmc.finder.global.error.exception.ErrorCode;

public class FailedToSendEmailException extends BusinessException {

    public FailedToSendEmailException() {
        super(ErrorCode.FAILED_TO_SEND_MAIL);
    }
}
