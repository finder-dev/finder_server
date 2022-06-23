package com.cmc.finder.domain.question.service;

import com.cmc.finder.domain.question.entity.Question;
import com.cmc.finder.domain.question.entity.QuestionImage;
import com.cmc.finder.domain.question.exception.QuestionImageNotFountException;
import com.cmc.finder.domain.question.repository.QuestionImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionImageService {

    private final QuestionImageRepository questionImageRepository;

    public void save(List<QuestionImage> questionImages) {
        questionImageRepository.saveAll(questionImages);
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
