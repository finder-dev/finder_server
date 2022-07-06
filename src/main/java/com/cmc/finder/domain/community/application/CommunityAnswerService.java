package com.cmc.finder.domain.community.application;

import com.cmc.finder.domain.community.entity.CommunityAnswer;
import com.cmc.finder.domain.community.repository.CommunityAnswerRepository;
import com.cmc.finder.global.error.exception.EntityNotFoundException;
import com.cmc.finder.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityAnswerService {

    private final CommunityAnswerRepository communityAnswerRepository;

    public List<CommunityAnswer> getAnswersByCommunityId(Long communityId) {
        return communityAnswerRepository.findAllByCommunityIdFetchUser(communityId);

    }

    public CommunityAnswer saveCommunityAnswer(CommunityAnswer saveCommunityAnswer) {

        return communityAnswerRepository.save(saveCommunityAnswer);
    }


    public CommunityAnswer getCommunityAnswerFetchUser(Long answerId) {

        return communityAnswerRepository.findByIdFetchUser(answerId)
                .orElseThrow(()-> new EntityNotFoundException(ErrorCode.COMMUNITY_ANSWER_NOT_FOUND));
    }

    @Transactional
    public void deleteCommunityAnswer(CommunityAnswer communityAnswer) {
        communityAnswerRepository.delete(communityAnswer);

    }

    @Transactional
    public CommunityAnswer updateCommunityAnswer(CommunityAnswer communityAnswer, CommunityAnswer updateCommunityAnswer) {

        communityAnswer.updateCommunityAnswer(updateCommunityAnswer);
        return communityAnswer;

    }


}
