package com.cmc.finder.domain.debate.service;

import com.cmc.finder.domain.answer.entity.Answer;
import com.cmc.finder.domain.answer.repostiory.AnswerRepository;
import com.cmc.finder.domain.debate.entity.Debate;
import com.cmc.finder.domain.debate.repository.DebateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DebateService {

    private final DebateRepository debateRepository;

    @Transactional
    public void create(Debate debate) {
        debateRepository.save(debate);
    }
}
