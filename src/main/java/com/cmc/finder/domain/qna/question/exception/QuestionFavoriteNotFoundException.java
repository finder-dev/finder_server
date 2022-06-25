package com.cmc.finder.domain.qna.question.exception;


import com.cmc.finder.global.error.exception.EntityNotFoundException;
import com.cmc.finder.global.error.exception.ErrorCode;

public class QuestionFavoriteNotFoundException extends EntityNotFoundException {

    public QuestionFavoriteNotFoundException() {
        super(ErrorCode.QUESETION_FAVORITE_NOT_EXISTS);
    }
}
