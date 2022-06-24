package com.cmc.finder.domain.debate.repository;

import com.cmc.finder.domain.debate.entity.Debate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DebateRepository extends JpaRepository<Debate, Long> {
}
