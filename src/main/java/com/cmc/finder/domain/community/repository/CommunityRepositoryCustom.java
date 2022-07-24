package com.cmc.finder.domain.community.repository;

import com.cmc.finder.api.community.dto.CommunitySearchDto;
import com.cmc.finder.api.community.dto.CommunitySimpleDto;
import com.cmc.finder.api.qna.qustion.dto.QuestionSimpleDto;
import com.cmc.finder.domain.community.entity.Community;
import com.cmc.finder.domain.model.MBTI;
import com.cmc.finder.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;


public interface CommunityRepositoryCustom {

    List<Community> findHotCommunity(Pageable pageable, User user);

    Slice<CommunitySimpleDto.Response> findPageCommunityByMBTI(Pageable pageable, String mbti, User user);

    Slice<CommunitySearchDto.Response> findSearchCommunity(Pageable pageable, String search, User user);

}
