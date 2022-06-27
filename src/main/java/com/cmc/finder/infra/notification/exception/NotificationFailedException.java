package com.cmc.finder.infra.notification.exception;

import com.cmc.finder.global.error.exception.BusinessException;
import com.cmc.finder.global.error.exception.ErrorCode;

public class NotificationFailedException extends BusinessException {

    public NotificationFailedException() {
        super(ErrorCode.NOTIFICATION_FAILED);
    }
}
