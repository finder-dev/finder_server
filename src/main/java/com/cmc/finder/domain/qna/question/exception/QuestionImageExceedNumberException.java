package com.cmc.finder.domain.qna.question.exception;

import com.cmc.finder.global.error.exception.BusinessException;
import com.cmc.finder.global.error.exception.ErrorCode;

public class QuestionImageExceedNumberException extends BusinessException {

    public QuestionImageExceedNumberException(String message) {
        super(message);
    }

    public QuestionImageExceedNumberException(ErrorCode errorCode) {
        super(errorCode);
    }
}
