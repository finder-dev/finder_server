package com.cmc.finder.domain.report.exception;

import com.cmc.finder.global.error.exception.BusinessException;
import com.cmc.finder.global.error.exception.ErrorCode;

public class AlreadyReceivedReportException extends BusinessException {

    public AlreadyReceivedReportException(ErrorCode errorCode) {
        super(errorCode);
    }
}
