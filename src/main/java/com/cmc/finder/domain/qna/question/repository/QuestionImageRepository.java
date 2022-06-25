package com.cmc.finder.domain.qna.question.repository;

import com.cmc.finder.domain.qna.question.entity.QuestionImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestionImageRepository extends JpaRepository<QuestionImage, Long> {

    Optional<QuestionImage> findByQuestionQuestionIdAndQuestionImgId(Long questionId, Long questionImgId);
}
