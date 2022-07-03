package com.cmc.finder.domain.community.application;

import com.cmc.finder.api.community.dto.CommunitySimpleDto;
import com.cmc.finder.api.qna.qustion.dto.QuestionSimpleDto;
import com.cmc.finder.domain.community.entity.Community;
import com.cmc.finder.domain.community.repository.CommunityRepository;
import com.cmc.finder.domain.model.MBTI;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityService {

    private final CommunityRepository communityRepository;

    @Transactional
    public Community createCommunity(Community saveCommunity) {
        return communityRepository.save(saveCommunity);
    }

    public Page<CommunitySimpleDto.Response> getCommunityList(Pageable pageable, MBTI mbti) {

        return communityRepository.findPageCommunityByMBTI(pageable, mbti);

    }



}
