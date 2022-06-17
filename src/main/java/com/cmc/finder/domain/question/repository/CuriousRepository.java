package com.cmc.finder.domain.question.repository;

import com.cmc.finder.domain.answer.entity.Answer;
import com.cmc.finder.domain.answer.entity.Helpful;
import com.cmc.finder.domain.question.entity.Curious;
import com.cmc.finder.domain.question.entity.Question;
import com.cmc.finder.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CuriousRepository extends JpaRepository<Curious, Long> {


    Optional<Curious> findByQuestionAndUser(Question question, User user);

    Boolean existsByQuestionAndUser(Question question, User user);

    void deleteByQuestionAndUser(Question question, User user);
}
