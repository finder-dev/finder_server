package com.cmc.finder.domain.debate.repository;

import com.cmc.finder.domain.debate.constant.DebateState;
import com.cmc.finder.domain.debate.entity.Debate;
import com.cmc.finder.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.domain.Pageable;
import java.util.List;


public interface DebateRepository extends JpaRepository<Debate, Long> {


    List<Debate> findByState(DebateState state);

    List<Debate> findAllByWriter(User user);

    @Query(value = "select d " +
            "from Debate d " +
            "join d.debaters da " +
            "order by da.size desc ")
    List<Debate> findHotDebate(Pageable pageable);
}
