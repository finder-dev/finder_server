package com.cmc.finder.api.qna.qustion.repository;

import com.cmc.finder.api.qna.qustion.dto.QuestionSimpleDto;
import com.cmc.finder.domain.answer.entity.Answer;
import com.cmc.finder.domain.answer.entity.QAnswer;
import com.cmc.finder.domain.question.entity.QQuestion;
import com.cmc.finder.domain.question.entity.QQuestionImage;
import com.cmc.finder.domain.question.entity.Question;
import com.cmc.finder.domain.user.entity.QUser;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class QuestionRepositoryImpl implements QuestionRepositoryCustom {

    private JPAQueryFactory queryFactory;

    public QuestionRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<QuestionSimpleDto> findQuestionSimpleDto(Pageable pageable) {
        QQuestion question = QQuestion.question;
        QUser user = QUser.user;
        QQuestionImage questionImage = QQuestionImage.questionImage;

        List<Question> results = queryFactory
                .select(question)
                .from(question)
                .join(question.questionImages, questionImage)
                .join(question.user, user).fetchJoin()
//                .where(
////                        questionImage.question.questionId.eq(question.questionId),
//                        questionImage.isRep.eq(Boolean.TRUE)
//                )
                .groupBy(question.questionId)
                .orderBy(question.createTime.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        int totalSize = queryFactory
                .selectFrom(question)
                .where()
                .fetch().size();

        List<QuestionSimpleDto> questionSimpleDtos = results.stream().map(question1 -> {
            return QuestionSimpleDto.of(question1);
        }).collect(Collectors.toList());

        return new PageImpl<>(questionSimpleDtos, pageable, totalSize);
    }

}
