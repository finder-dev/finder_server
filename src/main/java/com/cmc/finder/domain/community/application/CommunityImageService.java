package com.cmc.finder.domain.community.application;

import com.cmc.finder.domain.community.entity.CommunityImage;
import com.cmc.finder.domain.community.exception.CommunityImageNotFountException;
import com.cmc.finder.domain.community.repository.CommunityImageRepository;
import com.cmc.finder.domain.debate.entity.DebateAnswer;
import com.cmc.finder.domain.debate.entity.DebateAnswerReply;
import com.cmc.finder.domain.debate.exception.DebateAnswerReplyNotFoundException;
import com.cmc.finder.domain.debate.repository.DebateAnswerReplyRepository;
import com.cmc.finder.domain.qna.answer.exception.ReplyNotFoundException;
import com.cmc.finder.domain.qna.question.entity.QuestionImage;
import com.cmc.finder.domain.qna.question.exception.QuestionImageNotFountException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityImageService {

    private final CommunityImageRepository communityImageRepository;

    public CommunityImage getCommunityImage(Long communityImgId) {

        return communityImageRepository.findById(communityImgId)
                .orElseThrow(CommunityImageNotFountException::new);

    }


    public CommunityImage save(CommunityImage communityImage) {

        return communityImageRepository.save(communityImage);

    }
}
