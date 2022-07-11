package com.cmc.finder.domain.community.application;

import com.cmc.finder.domain.community.entity.Community;
import com.cmc.finder.domain.community.entity.SaveCommunity;
import com.cmc.finder.domain.community.repository.SaveCommunityRepository;
import com.cmc.finder.domain.user.entity.User;
import com.cmc.finder.global.error.exception.EntityNotFoundException;
import com.cmc.finder.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SaveCommunityService {

    private final SaveCommunityRepository saveCommunityRepository;

    public Boolean existsUser(Community community, User user) {
        return saveCommunityRepository.existsByCommunityAndUser(community, user);

    }

    @Transactional
    public void removeCommunity(Community community, User user) {

        SaveCommunity saveCommunity = saveCommunityRepository.findByCommunityAndUser(community, user)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.SAVE_COMMUNITY_NOT_FOUND));

        saveCommunityRepository.delete(saveCommunity);

    }

    public Slice<SaveCommunity> getSaveCommunityFetchCommunity(User user, Pageable pageable) {

        return saveCommunityRepository.findAllByUserFetchCommunity(user, pageable);

    }
}
