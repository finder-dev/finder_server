package com.cmc.finder.domain.debate.application;

import com.cmc.finder.domain.debate.entity.DebateAnswer;
import com.cmc.finder.domain.debate.entity.DebateAnswerReply;
import com.cmc.finder.domain.debate.repository.DebateAnswerReplyRepository;
import com.cmc.finder.global.error.exception.EntityNotFoundException;
import com.cmc.finder.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DebateAnswerReplyService {

    private final DebateAnswerReplyRepository debateAnswerReplyRepository;

    @Transactional
    public DebateAnswerReply create(DebateAnswerReply debateAnswerReply) {

        return debateAnswerReplyRepository.save(debateAnswerReply);
    }


    public DebateAnswerReply getDebateReplyFetchUser(Long debateReplyId) {
        return debateAnswerReplyRepository.findByIdFetchUser(debateReplyId)
                .orElseThrow(()->new EntityNotFoundException(ErrorCode.DEBATE_ANSWER_REPLY_NOT_EXISTS));
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
