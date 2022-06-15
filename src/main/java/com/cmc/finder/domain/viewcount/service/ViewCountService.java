package com.cmc.finder.domain.viewcount.service;


import com.cmc.finder.domain.model.Email;
import com.cmc.finder.domain.question.entity.Question;
import com.cmc.finder.domain.user.entity.User;
import com.cmc.finder.domain.viewcount.entity.ViewCount;
import com.cmc.finder.domain.viewcount.repository.ViewCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ViewCountService {

    private final ViewCountRepository viewCountRepository;


    public void addViewCount(ViewCount viewCount) {

        viewCountRepository.save(viewCount);
    }

    public Boolean alreadyReadUser(Question question, User user) {

        return viewCountRepository.existsByQuestionAndUser(question, user);
    }

    public Long getViewCount(Question question) {

        return viewCountRepository.getViewCounts(question);
    }
}
