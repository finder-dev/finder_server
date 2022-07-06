package com.cmc.finder.domain.community.exception;

import com.cmc.finder.global.error.exception.BusinessException;
import com.cmc.finder.global.error.exception.ErrorCode;

public class CommunityImageExceedNumberException extends BusinessException {

    public CommunityImageExceedNumberException(String message) {
        super(message);
    }

    public CommunityImageExceedNumberException(ErrorCode errorCode) {
        super(errorCode);
    }
}
