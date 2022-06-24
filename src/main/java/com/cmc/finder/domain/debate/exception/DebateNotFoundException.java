package com.cmc.finder.domain.debate.exception;


import com.cmc.finder.global.error.exception.EntityNotFoundException;
import com.cmc.finder.global.error.exception.ErrorCode;

public class DebateNotFoundException extends EntityNotFoundException {

    public DebateNotFoundException() {
        super(ErrorCode.DEBATE_NOT_EXISTS);
    }
}
