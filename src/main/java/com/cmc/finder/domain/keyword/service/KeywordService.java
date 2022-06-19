package com.cmc.finder.domain.keyword.service;

import com.cmc.finder.domain.keyword.entity.Keyword;
import com.cmc.finder.domain.keyword.repository.KeywordRepository;
import com.cmc.finder.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KeywordService {

    private final KeywordRepository keywordRepository;

    public List<Keyword> getKeywordByMember(User user) {
        return keywordRepository.findByUser(user);
//                .orElseThrow(MemberNotFoundException::new);
    }

    @Transactional
    public void save(Keyword keyword) {

        keywordRepository.save(keyword);
    }
}
