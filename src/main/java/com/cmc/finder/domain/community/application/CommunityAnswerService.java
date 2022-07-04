package com.cmc.finder.domain.community.application;

import com.cmc.finder.domain.community.entity.CommunityAnswer;
import com.cmc.finder.domain.community.exception.CommunityAnswerNotFoundException;
import com.cmc.finder.domain.community.repository.CommunityAnswerRepository;
import com.cmc.finder.domain.debate.entity.Debate;
import com.cmc.finder.domain.debate.entity.DebateAnswer;
import com.cmc.finder.domain.debate.exception.DebateAnswerNotFoundException;
import com.cmc.finder.domain.debate.exception.DebateNotFoundException;
import com.cmc.finder.domain.debate.repository.DebateAnswerRepository;
import com.cmc.finder.domain.qna.answer.entity.Answer;
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
                .orElseThrow(CommunityAnswerNotFoundException::new);
    }

    public void deleteCommunityAnswer(CommunityAnswer communityAnswer) {
        communityAnswerRepository.delete(communityAnswer);

    }

    public CommunityAnswer updateCommunityAnswer(CommunityAnswer communityAnswer, CommunityAnswer updateCommunityAnswer) {

        communityAnswer.updateCommunityAnswer(updateCommunityAnswer);
        return communityAnswer;


    }
}
