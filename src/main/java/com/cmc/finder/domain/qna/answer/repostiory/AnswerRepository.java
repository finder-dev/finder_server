package com.cmc.finder.domain.qna.answer.repostiory;

import com.cmc.finder.domain.qna.answer.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    //TODO answerImg fetch join시 중복 문제

    @Query("select a from Answer a " +
//            "join fetch a.answerImages ai " +
            "join fetch a.user u " +
            "where a.question.questionId=:questionId ")
    List<Answer> findAllByQuestionId(Long questionId);

}
