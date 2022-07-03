package com.cmc.finder.domain.qna.question.repository;

import com.cmc.finder.api.qna.qustion.dto.QuestionSimpleDto;
import com.cmc.finder.domain.model.MBTI;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface QuestionRepositoryCustom {

    Page<QuestionSimpleDto.Response> findPageQuestionByMBTI(Pageable pageable, MBTI mbti);

}
