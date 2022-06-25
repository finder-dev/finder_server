package com.cmc.finder.domain.debate.exception;


import com.cmc.finder.global.error.exception.EntityNotFoundException;
import com.cmc.finder.global.error.exception.ErrorCode;

public class DebateAnswerNotFoundException extends EntityNotFoundException {

    public DebateAnswerNotFoundException() {
        super(ErrorCode.DEBATE_ANSWER_NOT_EXISTS);
    }
}
