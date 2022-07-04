package com.cmc.finder.domain.qna.question.application;

import com.cmc.finder.api.qna.qustion.dto.QuestionSimpleDto;
import com.cmc.finder.domain.model.MBTI;
import com.cmc.finder.domain.qna.question.entity.Question;
import com.cmc.finder.domain.qna.question.exception.QuestionNotFountException;
import com.cmc.finder.domain.qna.question.repository.QuestionRepository;
import com.cmc.finder.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Transactional
    public Question create(Question question) {
        return questionRepository.save(question);
    }

    public Question getQuestionFetchUser(Long questionId) {
        return questionRepository.findByQuestionIdFetchUser(questionId)
                .orElseThrow(QuestionNotFountException::new);
    }

    public Question getQuestion(Long questionId) {

        return questionRepository.findById(questionId)
                .orElseThrow(QuestionNotFountException::new);

    }

    public List<Question> getQuestionsByUser(User user) {

        return questionRepository.findAllByUser(user);

    }

    public Question updateQuestion(Long questionId, Question updateQuestion) {
        Question savedQuestion = getQuestion(questionId);
        savedQuestion.updateQuestion(updateQuestion);
        return savedQuestion;

    }

    @Transactional
    public void deleteQuestion(Question question) {
        questionRepository.delete(question);
    }

    public List<Question> getHotQuestion() {

        return questionRepository.findHotQuestions(PageRequest.of(0, 5));

    }

    public Page<QuestionSimpleDto.Response> getQuestionList(Pageable pageable, MBTI mbti) {

        return questionRepository.findPageQuestionByMBTI(pageable, mbti);

    }
}
