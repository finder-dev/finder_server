package com.cmc.finder.domain.debate.exception;


import com.cmc.finder.global.error.exception.EntityNotFoundException;
import com.cmc.finder.global.error.exception.ErrorCode;

public class DebateAnswerReplyNotFoundException extends EntityNotFoundException {

    public DebateAnswerReplyNotFoundException() {
        super(ErrorCode.DEBATE_ANSWER_REPLY_NOT_EXISTS);
    }
}
