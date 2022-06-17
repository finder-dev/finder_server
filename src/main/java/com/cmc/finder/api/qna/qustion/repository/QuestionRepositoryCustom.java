package com.cmc.finder.api.qna.qustion.repository;

import com.cmc.finder.api.qna.qustion.dto.QuestionSimpleDto;
import com.cmc.finder.domain.model.MBTI;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface QuestionRepositoryCustom {

    Page<QuestionSimpleDto.Response> findQuestionSimpleDto(Pageable pageable, MBTI mbti);
}
