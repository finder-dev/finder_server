package com.cmc.finder.domain.qna.question.exception;


import com.cmc.finder.global.error.exception.EntityNotFoundException;
import com.cmc.finder.global.error.exception.ErrorCode;

public class QuestionImageNotFountException extends EntityNotFoundException {

    public QuestionImageNotFountException() {
        super(ErrorCode.QUESTION_IMAGE_NOT_EXISTS);
    }
}
