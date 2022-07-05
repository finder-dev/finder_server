package com.cmc.finder.domain.community.exception;


import com.cmc.finder.global.error.exception.EntityNotFoundException;
import com.cmc.finder.global.error.exception.ErrorCode;

public class SaveCommunityNotFoundException extends EntityNotFoundException {

    public SaveCommunityNotFoundException() {
        super(ErrorCode.SAVE_COMMUNITY_NOT_EXISTS);
    }
}
