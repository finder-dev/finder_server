package com.cmc.finder.domain.qna.question.exception;

import com.cmc.finder.global.error.exception.BusinessException;
import com.cmc.finder.global.error.exception.ErrorCode;

public class QuestionImageMaxException extends BusinessException {

    public QuestionImageMaxException(String message) {
        super(message);
    }

    public QuestionImageMaxException(ErrorCode errorCode) {
        super(errorCode);
    }
}
