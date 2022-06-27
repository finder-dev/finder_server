package com.cmc.finder.domain.qna.question.service;

import com.cmc.finder.domain.qna.question.entity.Question;
import com.cmc.finder.domain.qna.question.exception.QuestionImageNotFountException;
import com.cmc.finder.domain.qna.question.entity.QuestionImage;
import com.cmc.finder.domain.qna.question.repository.QuestionImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionImageService {

    private final QuestionImageRepository questionImageRepository;

    public QuestionImage save(QuestionImage saveQuestionImage) {
        return questionImageRepository.save(saveQuestionImage);
    }

//    public List<QuestionImage> getQuestionImageByQuestionId(Long questionId) {
//        return questionImageRepository.findAllByQuestionQuestionId(questionId);
//    }

    public QuestionImage getQuestionImage(Long questionId, Long questionImageId){
        return questionImageRepository.findByQuestionQuestionIdAndQuestionImgId(questionId, questionImageId)
                .orElseThrow(QuestionImageNotFountException::new);
    }

    @Transactional
    public void delete(QuestionImage questionImage) {
        questionImageRepository.delete(questionImage);
    }
}
