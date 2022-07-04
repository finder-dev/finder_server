package com.cmc.finder.domain.community.exception;

import com.cmc.finder.global.error.exception.BusinessException;
import com.cmc.finder.global.error.exception.ErrorCode;

public class CommunityImageMaxException extends BusinessException {

    public CommunityImageMaxException(String message) {
        super(message);
    }

    public CommunityImageMaxException(ErrorCode errorCode) {
        super(errorCode);
    }
}
