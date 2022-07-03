package com.cmc.finder.domain.community.repository;

import com.cmc.finder.api.community.dto.CommunitySimpleDto;
import com.cmc.finder.api.qna.qustion.dto.QuestionSimpleDto;
import com.cmc.finder.domain.community.entity.Community;
import com.cmc.finder.domain.community.entity.QCommunity;
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
import static com.cmc.finder.domain.community.entity.QCommunity.community;

import static com.cmc.finder.domain.user.entity.QUser.user;

@Repository
public class CommunityRepositoryImpl implements CommunityRepositoryCustom {

    private JPAQueryFactory queryFactory;

    public CommunityRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<CommunitySimpleDto.Response> findPageCommunityByMBTI(Pageable pageable, MBTI mbti) {
        List<Community> results = queryFactory
                .select(community)
                .from(community)
                .join(community.user, user).fetchJoin()
                .where(
                        searchByMBTI(mbti)
                )
                .groupBy(community.communityId)
                .orderBy(listSort(pageable))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        int totalSize = queryFactory
                .selectFrom(community)
                .where(
                        searchByMBTI(mbti)
                )
                .fetch().size();

        List<CommunitySimpleDto.Response> communitySimpleDtos = results.stream().map(community ->
                        CommunitySimpleDto.Response.of(community)
                ).collect(Collectors.toList());

        return new PageImpl<>(communitySimpleDtos, pageable, totalSize);
    }

    private BooleanExpression searchByMBTI(MBTI mbti) {

        return question.mbti.eq(mbti);

    }

    private OrderSpecifier<?> listSort(Pageable pageable) {

        if (!pageable.getSort().isEmpty()) {
            for (Sort.Order order : pageable.getSort()) {
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
                switch (order.getProperty()) {
                    case "ANSWER_COUNT":
                        return new OrderSpecifier<>(direction, community.communityAnswers.size());
                    case "CREATE_TIME":
                        return new OrderSpecifier<>(direction, community.createTime);
                }
            }
        }
        return null;
    }

}
