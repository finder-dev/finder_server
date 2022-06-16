package com.cmc.finder.domain.answer.exception;


import com.cmc.finder.global.error.exception.EntityNotFoundException;
import com.cmc.finder.global.error.exception.ErrorCode;

public class HelpfulNotFoundException extends EntityNotFoundException {

    public HelpfulNotFoundException() {
        super(ErrorCode.HELPFUL_NOT_EXISTS);
    }
}
