package com.cmc.finder.domain.qna.answer.exception;


import com.cmc.finder.global.error.exception.EntityNotFoundException;
import com.cmc.finder.global.error.exception.ErrorCode;

public class ReplyNotFoundException extends EntityNotFoundException {

    public ReplyNotFoundException() {
        super(ErrorCode.REPLY_NOT_EXISTS);
    }
}
