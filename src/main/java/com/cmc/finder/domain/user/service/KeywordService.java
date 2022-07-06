package com.cmc.finder.domain.user.service;

import com.cmc.finder.domain.user.entity.Keyword;
import com.cmc.finder.domain.user.repository.KeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KeywordService {

    private final KeywordRepository keywordRepository;

    @Transactional
    public void save(Keyword keyword) {
        keywordRepository.save(keyword);
    }
}
