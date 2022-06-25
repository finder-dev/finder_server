package com.cmc.finder.domain.qna.question.exception;


import com.cmc.finder.global.error.exception.EntityNotFoundException;
import com.cmc.finder.global.error.exception.ErrorCode;

public class QuestionNotFountException extends EntityNotFoundException {

    public QuestionNotFountException() {
        super(ErrorCode.QUESTION_NOT_EXISTS);
    }
}
