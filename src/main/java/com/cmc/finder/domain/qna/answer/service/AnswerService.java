package com.cmc.finder.domain.qna.answer.service;

import com.cmc.finder.domain.qna.answer.entity.Answer;
import com.cmc.finder.domain.qna.answer.repostiory.AnswerRepository;
import com.cmc.finder.global.error.exception.EntityNotFoundException;
import com.cmc.finder.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnswerService {

    private final AnswerRepository answerRepository;

    @Transactional
    public Answer create(Answer answer) {
        return answerRepository.save(answer);
    }

    public List<Answer> getAnswersByQuestionId(Long questionId) {

        return answerRepository.findAllByQuestionId(questionId);
    }

    public Answer getAnswer(Long answerId) {
        return answerRepository.findById(answerId)
                .orElseThrow(()-> new EntityNotFoundException(ErrorCode.ANSWER_NOT_FOUND));
    }

    public Answer getAnswerFetchQuestion(Long answerId) {
        return answerRepository.findById(answerId)
                .orElseThrow(()-> new EntityNotFoundException(ErrorCode.ANSWER_NOT_FOUND));

    }

    @Transactional
    public void deleteAnswer(Answer answer) {

        answerRepository.delete(answer);
    }


}
