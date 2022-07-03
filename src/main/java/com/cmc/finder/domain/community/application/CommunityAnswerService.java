package com.cmc.finder.domain.community.application;

import com.cmc.finder.domain.debate.entity.Debate;
import com.cmc.finder.domain.debate.entity.DebateAnswer;
import com.cmc.finder.domain.debate.exception.DebateAnswerNotFoundException;
import com.cmc.finder.domain.debate.repository.DebateAnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityAnswerService {

    private final DebateAnswerRepository debateAnswerRepository;

    @Transactional
    public DebateAnswer saveDebateAnswer(DebateAnswer saveDebateAnswer) {
        return debateAnswerRepository.save(saveDebateAnswer);
    }

    public List<DebateAnswer> getDebateAnswersByDebate(Debate debate) {
        return debateAnswerRepository.findByDebate(debate);

    }

    public DebateAnswer getDebateAnswer(Long debateAnswerId) {
        return debateAnswerRepository.findById(debateAnswerId)
                .orElseThrow(DebateAnswerNotFoundException::new);
    }


    @Transactional
    public void deleteDebateAnswer(DebateAnswer debateAnswer) {
        debateAnswerRepository.delete(debateAnswer);
    }
}
