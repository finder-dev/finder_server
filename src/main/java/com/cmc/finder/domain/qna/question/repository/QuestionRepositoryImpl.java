package com.cmc.finder.domain.qna.question.repository;

import com.cmc.finder.api.qna.qustion.dto.QuestionSimpleDto;
import com.cmc.finder.domain.model.MBTI;
import com.cmc.finder.domain.qna.question.entity.Question;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

import static com.cmc.finder.domain.qna.question.entity.QQuestion.question;
import static com.cmc.finder.domain.user.entity.QUser.user;

@Repository
public class QuestionRepositoryImpl implements QuestionRepositoryCustom {

    private JPAQueryFactory queryFactory;

    public QuestionRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<QuestionSimpleDto.Response> findPageQuestionByMBTI(Pageable pageable, MBTI mbti) {
        List<Question> results = queryFactory
                .select(question)
                .from(question)
                .join(question.user, user).fetchJoin()
                .where(
                        searchByMBTI(mbti)
                )
                .groupBy(question.questionId)
                .orderBy(listSort(pageable))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        int totalSize = queryFactory
                .selectFrom(question)
                .where(
                        searchByMBTI(mbti)
                )
                .fetch().size();

        List<QuestionSimpleDto.Response> questionSimpleDtos = results.stream().map(question1 ->
                        QuestionSimpleDto.Response.of(question1)
                ).collect(Collectors.toList());

        return new PageImpl<>(questionSimpleDtos, pageable, totalSize);
    }

    private BooleanExpression searchByMBTI(MBTI mbti) {

        return question.mbti.eq(mbti);

    }

    private OrderSpecifier<?> listSort(Pageable pageable) {

        if (!pageable.getSort().isEmpty()) {

            for (Sort.Order order : pageable.getSort()) {
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
                switch (order.getProperty()) {
                    case "VIEW_COUNT":
                        return new OrderSpecifier<>(direction, question.viewCounts.size());
                    case "CREATE_TIME":
                        return new OrderSpecifier<>(direction, question.createTime);
                }
            }
        }
        return null;
    }

}
