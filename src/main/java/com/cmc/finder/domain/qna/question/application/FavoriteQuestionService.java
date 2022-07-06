package com.cmc.finder.domain.qna.question.application;

import com.cmc.finder.domain.qna.question.entity.FavoriteQuestion;
import com.cmc.finder.domain.qna.question.entity.Question;
import com.cmc.finder.domain.qna.question.repository.FavoriteQuestionRepository;
import com.cmc.finder.domain.user.entity.User;
import com.cmc.finder.global.error.exception.EntityNotFoundException;
import com.cmc.finder.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FavoriteQuestionService {

    private final FavoriteQuestionRepository favoriteQuestionRepository;

    public Boolean existsUser(Question question, User user) {

        return favoriteQuestionRepository.existsByQuestionAndUser(question, user);

    }

    @Transactional
    public void create(FavoriteQuestion favoriteQuestion) {
        favoriteQuestionRepository.save(favoriteQuestion);
    }


    @Transactional
    public void delete(Question question, User user) {

        FavoriteQuestion favoriteQuestion = favoriteQuestionRepository.findByQuestionAndUser(question, user)
                .orElseThrow(()-> new EntityNotFoundException(ErrorCode.FAVORITE_QUESTION_NOT_FOUND));

        favoriteQuestionRepository.delete(favoriteQuestion);

    }

    public List<FavoriteQuestion> getFavoriteQuestionFetchQuestion(User user) {

        return favoriteQuestionRepository.findAllByUserFetchQuestion(user);

    }
}
