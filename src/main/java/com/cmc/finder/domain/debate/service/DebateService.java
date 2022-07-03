package com.cmc.finder.domain.debate.service;

import com.cmc.finder.domain.debate.constant.DebateState;
import com.cmc.finder.domain.debate.entity.Debate;
import com.cmc.finder.domain.debate.exception.DebateNotFoundException;
import com.cmc.finder.domain.debate.repository.DebateRepository;
import com.cmc.finder.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DebateService {

    private final DebateRepository debateRepository;

    @Transactional
    public Debate saveDebate(Debate saveDebate) {

        return debateRepository.save(saveDebate);
    }

    public Debate getDebate(Long debateId) {

        return debateRepository.findById(debateId)
                .orElseThrow(DebateNotFoundException::new);

    }

    /* 초 분 시 일 월 주 년(생략) */
    @Scheduled(cron = "0 0 0 * * *") // 매일 0시
    @Transactional
    public void updateClosedDebateStatus() {

        List<Debate> debateList = debateRepository.findByState(DebateState.PROCEEDING);

        debateList.stream().forEach(debate -> {
            // D-0: > 7, D-1: >= 7
            if (LocalDateTime.now().compareTo(debate.getCreateTime()) > 7) {
                debate.updateDebateState(DebateState.COMPLETE);
            }
        });

    }

    @Transactional
    public void deleteDebate(Debate debate) {

        debateRepository.delete(debate);
    }

    public List<Debate> getDebateByUser(User user) {

        return debateRepository.findAllByWriter(user);
    }

    public Debate getHotDebate() {

        List<Debate> hotDebate = debateRepository.findHotDebate(PageRequest.of(0, 1));

        if (hotDebate.size() == 0) {
            throw new DebateNotFoundException();
        }

        return hotDebate.get(0);

    }
}
