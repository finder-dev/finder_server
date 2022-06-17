package com.cmc.finder.domain.answer.repostiory;

import com.cmc.finder.domain.answer.entity.Answer;
import com.cmc.finder.domain.answer.entity.Helpful;
import com.cmc.finder.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface HelpfulRepository extends JpaRepository<Helpful, Long> {

    @Query(
            value = "select count(h) " +
                    "from Helpful h " +
                    "where h.answer=:answer "
    )
    Long getHelpfulCounts(@Param("answer") Answer answer);

    Optional<Helpful> findByAnswerAndUser(Answer answer, User user);

    void deleteByAnswerAndUser(Answer answer, User user);

    Boolean existsByAnswerAndUser(Answer answer, User user);

}
