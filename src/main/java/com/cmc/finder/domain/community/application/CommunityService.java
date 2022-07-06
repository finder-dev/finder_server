package com.cmc.finder.domain.community.application;

import com.cmc.finder.api.community.dto.CommunitySearchDto;
import com.cmc.finder.api.community.dto.CommunitySimpleDto;
import com.cmc.finder.domain.community.entity.Community;
import com.cmc.finder.domain.community.repository.CommunityRepository;
import com.cmc.finder.domain.user.entity.User;
import com.cmc.finder.global.error.exception.EntityNotFoundException;
import com.cmc.finder.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Community getCommunity(Long communityId) {
        return communityRepository.findById(communityId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.COMMUNITY_NOT_EXISTS));
    }

    public Page<CommunitySimpleDto.Response> getCommunityList(Pageable pageable, String mbti) {

        return communityRepository.findPageCommunityByMBTI(pageable, mbti);

    }

    public List<Community> getHotCommunity() {

        return communityRepository.findHotCommunity(PageRequest.of(0, 5));

    }

    public Community getCommunityFetchUser(Long communityId) {

        return communityRepository.findByCommunityIdFetchUser(communityId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.COMMUNITY_NOT_EXISTS));

    }

    @Transactional
    public Community updateCommunity(Long communityId, Community updateCommunity) {

        Community savedCommunity = getCommunity(communityId);
        savedCommunity.updateCommunity(updateCommunity);
        return savedCommunity;


    }

    @Transactional
    public void deleteCommunity(Community community) {

        communityRepository.delete(community);
    }

    public Page<CommunitySearchDto.Response> getSearchCommunityList(Pageable pageable, String search) {

        return communityRepository.findSearchCommunity(pageable, search);

    }

    public Page<Community> getCommunityByUser(User user, Pageable pageable) {

        return communityRepository.findAllByUserOrderByCommunityIdDesc(user, pageable);
    }

    public Page<Community> getCommunityByCommentUser(User user, Pageable pageable) {

        return communityRepository.findAllByCommentUserFetchUser(user, pageable);

    }
}
