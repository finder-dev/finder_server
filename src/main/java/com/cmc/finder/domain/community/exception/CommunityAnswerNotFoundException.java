package com.cmc.finder.domain.community.exception;


import com.cmc.finder.global.error.exception.EntityNotFoundException;
import com.cmc.finder.global.error.exception.ErrorCode;

public class CommunityAnswerNotFoundException extends EntityNotFoundException {

    public CommunityAnswerNotFoundException() {
        super(ErrorCode.COMMUNITY_ANSWER_NOT_EXISTS);
    }
}
