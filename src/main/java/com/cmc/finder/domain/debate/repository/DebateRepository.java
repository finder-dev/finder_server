package com.cmc.finder.domain.debate.repository;

import com.cmc.finder.domain.debate.constant.DebateState;
import com.cmc.finder.domain.debate.entity.Debate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface DebateRepository extends JpaRepository<Debate, Long> {


    List<Debate> findByState(DebateState state);

}
