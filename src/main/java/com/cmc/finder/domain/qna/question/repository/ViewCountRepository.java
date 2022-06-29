package com.cmc.finder.domain.qna.question.repository;

import com.cmc.finder.domain.qna.question.entity.Curious;
import com.cmc.finder.domain.qna.question.entity.Question;
import com.cmc.finder.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ViewCountRepository extends JpaRepository<Curious.ViewCount, Long> {

    boolean existsByQuestionAndUser(Question question, User user);

    List<Curious.ViewCount> findAllByQuestion(Question question);

    @Query(
            value = "select count(vc) " +
                    "from ViewCount vc " +
                    "where vc.question=:question "
    )
    Long getViewCounts(@Param("question") Question question);
}
