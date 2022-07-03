package com.cmc.finder.domain.community.application;

import com.cmc.finder.domain.debate.entity.DebateAnswer;
import com.cmc.finder.domain.debate.entity.DebateAnswerReply;
import com.cmc.finder.domain.debate.exception.DebateAnswerReplyNotFoundException;
import com.cmc.finder.domain.debate.repository.DebateAnswerReplyRepository;
import com.cmc.finder.domain.qna.answer.exception.ReplyNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityImageService {

    private final DebateAnswerReplyRepository debateAnswerReplyRepository;

    @Transactional
    public DebateAnswerReply create(DebateAnswerReply debateAnswerReply) {

        return debateAnswerReplyRepository.save(debateAnswerReply);
    }

    public DebateAnswerReply getDebateReply(Long debateAnswerReplyId) {
        return debateAnswerReplyRepository.findById(debateAnswerReplyId)
                .orElseThrow(ReplyNotFoundException::new);
    }

    public DebateAnswerReply getDebateReplyFetchUser(Long debateReplyId) {
        return debateAnswerReplyRepository.findByIdFetchUser(debateReplyId)
                .orElseThrow(DebateAnswerReplyNotFoundException::new);
    }

    public List<DebateAnswerReply> getDebateReplyByAnswerFetchUser(DebateAnswer debateAnswer) {
        return debateAnswerReplyRepository.findAllByDebateAnswerFetchUser(debateAnswer);
    }

    @Transactional
    public void deleteDebateReply(DebateAnswerReply debateAnswerReply) {
        debateAnswerReplyRepository.delete(debateAnswerReply);
    }

    @Transactional
    public DebateAnswerReply updateDebateReply(DebateAnswerReply debateAnswerReply, DebateAnswerReply updateDebateReply) {
        debateAnswerReply.updateReply(updateDebateReply);
        return debateAnswerReply;
    }
}
