package com.cmc.finder.api.qna.qustion.repository;

import com.cmc.finder.api.qna.qustion.dto.QuestionSimpleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface QuestionRepositoryCustom {
    // TODO 필터링 조건 추가
    Page<QuestionSimpleDto> findQuestionSimpleDto(Pageable pageable);
}
