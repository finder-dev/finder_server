package com.cmc.finder.domain.answer.exception;


import com.cmc.finder.global.error.exception.EntityNotFoundException;
import com.cmc.finder.global.error.exception.ErrorCode;

public class AnswerNotFoundException extends EntityNotFoundException {

    public AnswerNotFoundException() {
        super(ErrorCode.ANSWER_NOT_EXISTS);
    }
}
