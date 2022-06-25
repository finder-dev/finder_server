package com.cmc.finder.domain.debate.repository;

import com.cmc.finder.domain.debate.constant.DebateState;
import com.cmc.finder.domain.debate.entity.Debate;
import com.cmc.finder.domain.debate.entity.DebateAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface DebateAnswerRepository extends JpaRepository<DebateAnswer, Long> {

    List<DebateAnswer> findByDebate(Debate debate);

}
