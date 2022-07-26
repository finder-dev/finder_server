package com.cmc.finder.domain.debate.repository;

import com.cmc.finder.domain.debate.entity.DebateAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface DebateAnswerRepository extends JpaRepository<DebateAnswer, Long> , DebateAnswerRepositoryCustom{


    @Query("select da from DebateAnswer da " +
            "join fetch da.user u " +
            "where da.debateAnswerId=:debateAnswerId ")
    Optional<DebateAnswer> findByIdFetchUser(Long debateAnswerId);
}
