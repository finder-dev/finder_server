package com.cmc.finder.domain.answer.service;

import com.cmc.finder.domain.answer.entity.Answer;
import com.cmc.finder.domain.answer.repostiory.AnswerRepository;
import com.cmc.finder.domain.question.entity.Question;
import com.cmc.finder.domain.question.exception.QuestionNotFountException;
import com.cmc.finder.domain.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnswerService {

    private final AnswerRepository answerRepository;

    public void create(Answer answer) {
        answerRepository.save(answer);
    }

    public List<Answer> getAnswersByQuestionId(Long questionId) {
        return answerRepository.findAllByQuestionId(questionId);

    }

//    public Question getAnswer(Long questionId) {
//
//        return answerRepository.findByQuestionIdFetchQuestionImage(questionId)
//                .orElseThrow(QuestionNotFountException::new);
//
//    }

}
