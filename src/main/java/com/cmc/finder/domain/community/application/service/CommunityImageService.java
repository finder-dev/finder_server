package com.cmc.finder.domain.community.application.service;

import com.cmc.finder.domain.community.entity.CommunityImage;
import com.cmc.finder.domain.community.repository.CommunityImageRepository;
import com.cmc.finder.global.error.exception.EntityNotFoundException;
import com.cmc.finder.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityImageService {

    private final CommunityImageRepository communityImageRepository;

    public CommunityImage getCommunityImage(Long communityImgId) {

        return communityImageRepository.findById(communityImgId)
                .orElseThrow(()-> new EntityNotFoundException(ErrorCode.COMMUNITY_IMAGE_NOT_FOUND));

    }


    public CommunityImage save(CommunityImage communityImage) {

        return communityImageRepository.save(communityImage);

    }
}
