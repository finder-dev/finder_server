package com.cmc.finder.domain.community.exception;


import com.cmc.finder.global.error.exception.EntityNotFoundException;
import com.cmc.finder.global.error.exception.ErrorCode;

public class CommunityNotFountException extends EntityNotFoundException {

    public CommunityNotFountException() {
        super(ErrorCode.COMMUNITY_NOT_EXISTS);
    }
}
