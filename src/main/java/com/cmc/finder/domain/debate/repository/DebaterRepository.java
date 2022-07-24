package com.cmc.finder.domain.debate.repository;

import com.cmc.finder.domain.debate.constant.Option;
import com.cmc.finder.domain.debate.entity.Debate;
import com.cmc.finder.domain.debate.entity.Debater;
import com.cmc.finder.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DebaterRepository extends JpaRepository<Debater, Long> {

    Boolean existsByDebateAndUser(Debate debate, User user);

    Optional<Debater> findByDebateAndUser(Debate debate, User user);

    Long countDebaterByDebateAndOption(Debate debate, Option option);


}
