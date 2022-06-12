package com.cmc.finder.domain.question.repository;

import com.cmc.finder.domain.question.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
