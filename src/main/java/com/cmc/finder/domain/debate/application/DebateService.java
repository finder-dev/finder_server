package com.cmc.finder.domain.debate.application;

import com.cmc.finder.api.debate.dto.DebateSimpleDto;
import com.cmc.finder.domain.debate.constant.DebateState;
import com.cmc.finder.domain.debate.entity.Debate;
import com.cmc.finder.domain.debate.exception.DebaterNotExistsException;
import com.cmc.finder.domain.debate.repository.DebateRepository;
import com.cmc.finder.global.error.exception.EntityNotFoundException;
import com.cmc.finder.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

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
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.DEBATE_NOT_FOUND));

    }

    /* 초 분 시 일 월 주 년(생략) */
    @Scheduled(cron = "0 0 0 * * *") // 매일 0시
    @Transactional
    public void updateClosedDebateStatus() {

        List<Debate> debateList = debateRepository.findByState(DebateState.PROCEEDING);

        debateList.stream().forEach(debate -> {
            // 6, 5, 4, 3, 2, 1, 0, 마감
            if (ChronoUnit.DAYS.between(debate.getCreateTime().toLocalDate(), LocalDateTime.now().toLocalDate()) > 6) {
                debate.updateDebateState(DebateState.COMPLETE);
            }

        });

    }


    public Debate getHotDebate() {

        List<Debate> hotDebate = debateRepository.findHotDebate(PageRequest.of(0, 1), DebateState.PROCEEDING);

        if (hotDebate.size() == 0) {
            throw new DebaterNotExistsException(ErrorCode.DEBATE_PARTICIPATE_DEBATER_NOT_EXISTS);
        }

        return hotDebate.get(0);

    }

    public Slice<DebateSimpleDto.Response> getDebateList(DebateState debateState, Pageable pageable) {

        return debateRepository.findDebateSimpleDto(debateState, pageable);

    }

}
