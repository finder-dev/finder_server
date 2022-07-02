package com.cmc.finder.domain.qna.answer.repostiory;

import com.cmc.finder.domain.qna.answer.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {


    @Query("select a from Answer a " +
            "join fetch a.user u " +
            "where a.question.questionId=:questionId ")
    List<Answer> findAllByQuestionId(Long questionId);

    @Query("select a from Answer a " +
            "join fetch a.question q " +
            "where a.answerId=:answerId ")
    Optional<Answer> findAnswerByAnswerIdFetchQuestion(Long answerId);
}
