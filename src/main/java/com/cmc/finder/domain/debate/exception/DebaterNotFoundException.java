package com.cmc.finder.domain.debate.exception;


import com.cmc.finder.global.error.exception.EntityNotFoundException;
import com.cmc.finder.global.error.exception.ErrorCode;

public class DebaterNotFoundException extends EntityNotFoundException {

    public DebaterNotFoundException() {
        super(ErrorCode.DEBATER_NOT_EXISTS);
    }
}
