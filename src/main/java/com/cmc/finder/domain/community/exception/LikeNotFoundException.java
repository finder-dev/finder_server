package com.cmc.finder.domain.community.exception;


import com.cmc.finder.global.error.exception.EntityNotFoundException;
import com.cmc.finder.global.error.exception.ErrorCode;

public class LikeNotFoundException extends EntityNotFoundException {

    public LikeNotFoundException() {
        super(ErrorCode.LIKE_NOT_EXISTS);
    }
}
