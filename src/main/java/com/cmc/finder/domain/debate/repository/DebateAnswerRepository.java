package com.cmc.finder.domain.debate.repository;

import com.cmc.finder.domain.debate.entity.Debate;
import com.cmc.finder.domain.debate.entity.DebateAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface DebateAnswerRepository extends JpaRepository<DebateAnswer, Long> {

    @Query("select da from DebateAnswer da " +
            "join fetch da.user u " +
            "where da.debate=:debate and da.parent is null "+
            "order by da.debateAnswerId desc ")
    List<DebateAnswer> findAllByDebateFetchUser(Debate debate);

}
