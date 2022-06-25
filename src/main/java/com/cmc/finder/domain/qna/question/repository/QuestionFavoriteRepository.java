package com.cmc.finder.domain.qna.question.repository;

import com.cmc.finder.domain.qna.question.entity.Question;
import com.cmc.finder.domain.qna.question.entity.QuestionFavorite;
import com.cmc.finder.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestionFavoriteRepository extends JpaRepository<QuestionFavorite, Long> {

    Optional<QuestionFavorite> findByQuestionAndUser(Question question, User user);

    void deleteByQuestionAndUser(Question question, User user);

    Boolean existsByQuestionAndUser(Question question, User user);
}
