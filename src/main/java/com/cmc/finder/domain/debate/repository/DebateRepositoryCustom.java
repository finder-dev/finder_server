package com.cmc.finder.domain.debate.repository;

import com.cmc.finder.api.debate.dto.DebateSimpleDto;
import com.cmc.finder.domain.debate.constant.DebateState;
import com.cmc.finder.domain.debate.entity.Debate;
import com.cmc.finder.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface DebateRepositoryCustom {

    Slice<DebateSimpleDto.Response> findDebateSimpleDto(DebateState state, User user, Pageable pageable);


    List<Debate> findHotDebate(Pageable pageable, User user);
}
