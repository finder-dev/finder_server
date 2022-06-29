package com.cmc.finder.domain.qna.question.repository;

import com.cmc.finder.domain.qna.question.entity.Question;
import com.cmc.finder.domain.user.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("select q from Question q " +
            "join fetch q.user qu " +
            "where q.questionId=:questionId ")
    Optional<Question> findByQuestionIdFetchUser(Long questionId);

    List<Question> findAllByUser(User user);

    @Query(value = "select q " +
            "from Question q " +
            "join q.answers a " +
            "group by q.questionId " +
            "order by a.size desc ")
    List<Question> findHotQuestions(Pageable pageable);


}
