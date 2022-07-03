package com.cmc.finder.domain.community.repository;

import com.cmc.finder.api.community.dto.CommunitySimpleDto;
import com.cmc.finder.api.qna.qustion.dto.QuestionSimpleDto;
import com.cmc.finder.domain.model.MBTI;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CommunityRepositoryCustom {

    Page<CommunitySimpleDto.Response> findPageCommunityByMBTI(Pageable pageable, MBTI mbti);

}
