package com.cmc.finder.domain.community.repository;

import com.cmc.finder.api.community.dto.CommunitySearchDto;
import com.cmc.finder.api.community.dto.CommunitySimpleDto;
import com.cmc.finder.api.qna.qustion.dto.QuestionSimpleDto;
import com.cmc.finder.domain.model.MBTI;
import com.cmc.finder.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CommunityRepositoryCustom {

    Page<CommunitySimpleDto.Response> findPageCommunityByMBTI(Pageable pageable, String mbti, User user);

    Page<CommunitySearchDto.Response> findSearchCommunity(Pageable pageable, String search);

}
