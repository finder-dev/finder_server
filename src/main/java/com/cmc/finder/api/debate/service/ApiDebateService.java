package com.cmc.finder.api.debate.service;

import com.cmc.finder.api.debate.dto.DebateCreateDto;
import com.cmc.finder.domain.debate.service.DebateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ApiDebateService {

    private final DebateService debateService;

    public DebateCreateDto.Response createDebate(DebateCreateDto.Request request, String email) {
        return null;

    }
}
