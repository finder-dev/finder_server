package com.cmc.finder.api.debate.repository;

import com.cmc.finder.api.debate.dto.DebateSimpleDto;
import com.cmc.finder.api.qna.qustion.dto.QuestionSimpleDto;
import com.cmc.finder.domain.model.MBTI;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface DebateRepositoryCustom {

    Page<DebateSimpleDto.Response> findDebateSimpleDto(Pageable pageable);
}
