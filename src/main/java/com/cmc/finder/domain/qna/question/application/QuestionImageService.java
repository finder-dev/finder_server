package com.cmc.finder.domain.qna.question.application;

import com.cmc.finder.domain.qna.question.exception.QuestionImageNotFountException;
import com.cmc.finder.domain.qna.question.entity.QuestionImage;
import com.cmc.finder.domain.qna.question.repository.QuestionImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionImageService {

    private final QuestionImageRepository questionImageRepository;

    public QuestionImage save(QuestionImage saveQuestionImage) {
        return questionImageRepository.save(saveQuestionImage);
    }

    public QuestionImage getQuestionImage(Long questionId, Long questionImageId){
        return questionImageRepository.findByQuestionQuestionIdAndQuestionImgId(questionId, questionImageId)
                .orElseThrow(QuestionImageNotFountException::new);
    }

    @Transactional
    public void delete(QuestionImage questionImage) {
        questionImageRepository.delete(questionImage);
    }
}
