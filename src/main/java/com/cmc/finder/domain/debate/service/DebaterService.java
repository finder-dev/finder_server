package com.cmc.finder.domain.debate.service;

import com.cmc.finder.domain.debate.entity.Debate;
import com.cmc.finder.domain.debate.entity.Debater;
import com.cmc.finder.domain.debate.exception.DebaterNotFoundException;
import com.cmc.finder.domain.debate.repository.DebaterRepository;
import com.cmc.finder.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DebaterService {

    private final DebaterRepository debaterRepository;

    @Transactional
    public Debater saveDebater(Debater saveDebater) {

        return debaterRepository.save(saveDebater);
    }

    public Boolean existsDebater(User user, Debate debate) {

        return debaterRepository.existsByDebateAndUser(debate, user);
    }

    public Debater getDebater(User user, Debate debate) {

        return debaterRepository.findByDebateAndUser(debate, user)
                .orElseThrow(DebaterNotFoundException::new);
    }

    public void deleteDebater(Debater debater) {

        debaterRepository.delete(debater);
    }
}
