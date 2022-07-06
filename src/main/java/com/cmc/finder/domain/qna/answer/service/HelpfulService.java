package com.cmc.finder.domain.qna.answer.service;

import com.cmc.finder.domain.qna.answer.entity.Answer;
import com.cmc.finder.domain.qna.answer.entity.Helpful;
import com.cmc.finder.domain.qna.answer.repostiory.HelpfulRepository;
import com.cmc.finder.domain.user.entity.User;
import com.cmc.finder.global.error.exception.EntityNotFoundException;
import com.cmc.finder.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HelpfulService {

    private final HelpfulRepository helpfulRepository;

    @Transactional
    public void create(Helpful helpful) {
        helpfulRepository.save(helpful);
    }

    public Long getHelpfulCount(Answer answer) {
        return helpfulRepository.getHelpfulCounts(answer);
    }


    @Transactional
    public void delete(Answer answer, User user) {

        Helpful helpful = helpfulRepository.findByAnswerAndUser(answer, user)
                .orElseThrow(()-> new EntityNotFoundException(ErrorCode.HELPFUL_NOT_EXISTS));

        helpfulRepository.delete(helpful);

    }

    public boolean existsUser(Answer answer, User user) {

        return helpfulRepository.existsByAnswerAndUser(answer, user);
    }
}
