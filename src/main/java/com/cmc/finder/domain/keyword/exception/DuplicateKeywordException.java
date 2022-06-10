package com.cmc.finder.domain.keyword.exception;

import com.cmc.finder.global.error.exception.BusinessException;
import com.cmc.finder.global.error.exception.ErrorCode;

public class DuplicateKeywordException extends BusinessException {

    public DuplicateKeywordException() {
        super(ErrorCode.DUPLICATE_KEYWORD);
    }
}
