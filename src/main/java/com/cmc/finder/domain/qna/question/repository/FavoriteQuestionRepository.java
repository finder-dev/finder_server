package com.cmc.finder.domain.qna.question.repository;

import com.cmc.finder.domain.qna.question.entity.FavoriteQuestion;
import com.cmc.finder.domain.qna.question.entity.Question;
import com.cmc.finder.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FavoriteQuestionRepository extends JpaRepository<FavoriteQuestion, Long> {

    Optional<FavoriteQuestion> findByQuestionAndUser(Question question, User user);

    Boolean existsByQuestionAndUser(Question question, User user);

    @Query("select fq "+
            "from FavoriteQuestion fq "+
            "join fetch fq.question q " +
            "where fq.user=:user " +
            "order by fq.questionFavoriteId desc ")
    List<FavoriteQuestion> findAllByUserFetchQuestion(User user);
}
