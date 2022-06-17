package com.cmc.finder.domain.answer.service;

import com.cmc.finder.domain.answer.entity.Answer;
import com.cmc.finder.domain.answer.entity.Helpful;
import com.cmc.finder.domain.answer.exception.HelpfulNotFoundException;
import com.cmc.finder.domain.answer.repostiory.HelpfulRepository;
import com.cmc.finder.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

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
        //TODO 연속 클릭 시 반응 가능?

        Helpful helpful = helpfulRepository.findByAnswerAndUser(answer, user)
                .orElseThrow(HelpfulNotFoundException::new);

        helpfulRepository.delete(helpful);

    }

    public boolean existsUser(Answer answer, User user) {

        return helpfulRepository.existsByAnswerAndUser(answer, user);
    }
}
