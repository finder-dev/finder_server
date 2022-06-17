package com.cmc.finder.domain.question.service;

import com.cmc.finder.domain.question.entity.Curious;
import com.cmc.finder.domain.question.entity.Question;
import com.cmc.finder.domain.question.entity.QuestionFavorite;
import com.cmc.finder.domain.question.exception.CuriousNotFoundException;
import com.cmc.finder.domain.question.exception.QuestionFavoriteNotFoundException;
import com.cmc.finder.domain.question.repository.CuriousRepository;
import com.cmc.finder.domain.question.repository.QuestionFavoriteRepository;
import com.cmc.finder.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionFavoriteService {

    private final QuestionFavoriteRepository questionFavoriteRepository;

    public Boolean existsUser(Question question, User user) {

        return questionFavoriteRepository.existsByQuestionAndUser(question, user);

    }

    @Transactional
    public void create(QuestionFavorite questionFavorite) {
        questionFavoriteRepository.save(questionFavorite);
    }


    @Transactional
    public void delete(Question question, User user) {

        QuestionFavorite questionFavorite = questionFavoriteRepository.findByQuestionAndUser(question, user)
                .orElseThrow(QuestionFavoriteNotFoundException::new);

        questionFavoriteRepository.delete(questionFavorite);

    }
}
