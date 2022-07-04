package com.cmc.finder.domain.qna.question.application;

import com.cmc.finder.domain.qna.question.entity.Curious;
import com.cmc.finder.domain.qna.question.entity.Question;
import com.cmc.finder.domain.qna.question.exception.CuriousNotFoundException;
import com.cmc.finder.domain.qna.question.repository.CuriousRepository;
import com.cmc.finder.domain.user.entity.User;
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
        //TODO 연속 클릭 시 반응 가능?

        Curious curious = curiousRepository.findByQuestionAndUser(question, user)
                .orElseThrow(CuriousNotFoundException::new);

        curiousRepository.delete(curious);

    }

    public Boolean existsUser(Question question, User user) {

        return curiousRepository.existsByQuestionAndUser(question, user);
    }
}
