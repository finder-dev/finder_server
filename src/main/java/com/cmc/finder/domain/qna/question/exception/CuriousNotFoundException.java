package com.cmc.finder.domain.qna.question.exception;


import com.cmc.finder.global.error.exception.EntityNotFoundException;
import com.cmc.finder.global.error.exception.ErrorCode;

public class CuriousNotFoundException extends EntityNotFoundException {

    public CuriousNotFoundException() {
        super(ErrorCode.CURIOUS_NOT_EXISTS);
    }
}
