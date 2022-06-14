package com.cmc.finder.domain.question.service;

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
public class QuestionService {

    private final QuestionRepository questionRepository;

    public void create(Question question) {
        questionRepository.save(question);
    }

    public List<Question> getQuestions() {

        return questionRepository.findAll();
    }

    public Question getQuestion(Long questionId) {

        return questionRepository.findById(questionId)
                .orElseThrow(QuestionNotFountException::new);

    }

    public Question getQuestionFetchQuestionImage(Long questionId) {

        return questionRepository.findByQuestionIdFetchQuestionImageAndUser(questionId)
                .orElseThrow(QuestionNotFountException::new);

    }

}
