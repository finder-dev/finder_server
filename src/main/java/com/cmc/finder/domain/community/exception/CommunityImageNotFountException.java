package com.cmc.finder.domain.community.exception;


import com.cmc.finder.global.error.exception.EntityNotFoundException;
import com.cmc.finder.global.error.exception.ErrorCode;

public class CommunityImageNotFountException extends EntityNotFoundException {

    public CommunityImageNotFountException() {
        super(ErrorCode.COMMUNITY_IMAGE_NOT_EXISTS);
    }
}
