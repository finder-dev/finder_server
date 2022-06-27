package com.cmc.finder.domain.qna.question.service;

import com.cmc.finder.domain.qna.question.entity.Question;
import com.cmc.finder.domain.qna.question.exception.QuestionNotFountException;
import com.cmc.finder.domain.qna.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Transactional
    public void create(Question question) {
        questionRepository.save(question);
    }

    public Question getQuestionFetchUser(Long questionId) {
        return questionRepository.findByQuestionIdFetchUser(questionId)
                .orElseThrow(QuestionNotFountException::new);
    }

    public Question getQuestion(Long questionId) {

        return questionRepository.findById(questionId)
                .orElseThrow(QuestionNotFountException::new);

    }

    public Question getQuestionFetchQuestionImage(Long questionId) {

        return questionRepository.findByQuestionIdFetchQuestionImageAndUser(questionId)
                .orElseThrow(QuestionNotFountException::new);

    }

    public Question updateQuestion(Question question, Question updatequestion) {

        question.updateQuestion(updatequestion);
        return question;


    }

    @Transactional
    public void deleteQuestion(Question question) {
        questionRepository.delete(question);
    }
}
