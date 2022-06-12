package com.cmc.finder.domain.question.service;

import com.cmc.finder.domain.question.entity.Question;
import com.cmc.finder.domain.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionService {

    private final QuestionRepository questionRepository;

    public void create(Question question) {
        questionRepository.save(question);
    }
}
