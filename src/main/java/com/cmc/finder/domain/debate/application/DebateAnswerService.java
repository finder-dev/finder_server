package com.cmc.finder.domain.debate.application;

import com.cmc.finder.domain.debate.entity.Debate;
import com.cmc.finder.domain.debate.entity.DebateAnswer;
import com.cmc.finder.domain.debate.repository.DebateAnswerRepository;
import com.cmc.finder.domain.user.entity.User;
import com.cmc.finder.global.error.exception.EntityNotFoundException;
import com.cmc.finder.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DebateAnswerService {

    private final DebateAnswerRepository debateAnswerRepository;

    @Transactional
    public DebateAnswer saveDebateAnswer(DebateAnswer saveDebateAnswer) {
        return debateAnswerRepository.save(saveDebateAnswer);
    }

    public List<DebateAnswer> getDebateAnswersByDebate(Debate debate, User user) {
        return debateAnswerRepository.findAllByDebateFetchUser(debate, user);

    }

    public DebateAnswer getDebateAnswerFetchUser(Long debateAnswerId) {
        return debateAnswerRepository.findByIdFetchUser(debateAnswerId)
                .orElseThrow(()-> new EntityNotFoundException(ErrorCode.DEBATE_ANSWER_NOT_FOUND));
    }

    public DebateAnswer getDebateAnswer(Long debateAnswerId) {
        return debateAnswerRepository.findById(debateAnswerId)
                .orElseThrow(()-> new EntityNotFoundException(ErrorCode.DEBATE_ANSWER_NOT_FOUND));
    }


    @Transactional
    public void deleteDebateAnswer(Long debateAnswerId) {
        DebateAnswer debateAnswer = getDebateAnswer(debateAnswerId);
        debateAnswerRepository.delete(debateAnswer);
    }

    @Transactional
    public DebateAnswer updateDebateAnswer(Long debateAnswerId, DebateAnswer updateDebateAnswer) {

        DebateAnswer debateAnswer = getDebateAnswer(debateAnswerId);
        debateAnswer.updateDebateAnswer(updateDebateAnswer);
        return debateAnswer;
    }
}
