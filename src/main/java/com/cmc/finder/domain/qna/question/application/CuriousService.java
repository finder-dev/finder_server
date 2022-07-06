package com.cmc.finder.domain.qna.question.application;

import com.cmc.finder.domain.qna.question.entity.Curious;
import com.cmc.finder.domain.qna.question.entity.Question;
import com.cmc.finder.domain.qna.question.repository.CuriousRepository;
import com.cmc.finder.domain.user.entity.User;
import com.cmc.finder.global.error.exception.EntityNotFoundException;
import com.cmc.finder.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CuriousService {

    private final CuriousRepository curiousRepository;

    @Transactional
    public void create(Curious curious) {
        curiousRepository.save(curious);
    }


    @Transactional
    public void delete(Question question, User user) {

        Curious curious = curiousRepository.findByQuestionAndUser(question, user)
                .orElseThrow(()-> new EntityNotFoundException(ErrorCode.CURIOUS_NOT_FOUND));

        curiousRepository.delete(curious);

    }

    public Boolean existsUser(Question question, User user) {

        return curiousRepository.existsByQuestionAndUser(question, user);
    }
}
