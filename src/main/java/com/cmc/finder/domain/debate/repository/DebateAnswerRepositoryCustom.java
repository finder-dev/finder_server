package com.cmc.finder.domain.debate.repository;

import com.cmc.finder.domain.debate.entity.Debate;
import com.cmc.finder.domain.debate.entity.DebateAnswer;
import com.cmc.finder.domain.user.entity.User;

import java.util.List;

public interface DebateAnswerRepositoryCustom {

    List<DebateAnswer> findAllByDebateFetchUser(Debate debate, User user);

}
