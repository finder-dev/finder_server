package com.cmc.finder.domain.qna.question.repository;

import com.cmc.finder.domain.qna.question.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {

//    @Query("select q from Question q "+
//            "join fetch q.questionImages qi "+
//            "join fetch q.user qu " +
//            "where q.questionId=:questionId ")
//    Optional<Question> findByQuestionIdFetchQuestionImageAndUser(Long questionId);

    @Query("select q from Question q "+
            "join fetch q.user qu " +
            "where q.questionId=:questionId ")
    Optional<Question> findByQuestionIdFetchUser(Long questionId);
}
