package com.cmc.finder.domain.question.service;

import com.cmc.finder.domain.question.entity.Question;
import com.cmc.finder.domain.question.entity.QuestionImage;
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


}
