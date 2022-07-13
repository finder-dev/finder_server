package com.cmc.finder.domain.community.repository;

import com.cmc.finder.api.community.dto.CommunitySearchDto;
import com.cmc.finder.api.community.dto.CommunitySimpleDto;
import com.cmc.finder.domain.community.entity.Community;
import com.cmc.finder.domain.model.MBTI;
import com.cmc.finder.domain.user.entity.User;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

import static com.cmc.finder.domain.community.entity.QCommunity.community;

import static com.cmc.finder.domain.user.entity.QUser.user;

@Repository
public class CommunityRepositoryImpl implements CommunityRepositoryCustom {

    private JPAQueryFactory queryFactory;

    public CommunityRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Slice<CommunitySimpleDto.Response> findPageCommunityByMBTI(Pageable pageable, String mbti, User curUser) {

        List<Community> results = queryFactory
                .select(community)
                .from(community)
                .join(community.user, user).fetchJoin()
                .where(
                       findByMBTI(mbti)
                )
                .groupBy(community.communityId)
                .orderBy(listSort(pageable))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        List<CommunitySimpleDto.Response> contents = results.stream().map(community ->
                CommunitySimpleDto.Response.of(community, curUser)
        ).collect(Collectors.toList());


        boolean hasNext = false;
        if (contents.size() > pageable.getPageSize()) {
            contents.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(contents, pageable, hasNext);


    }

    @Override
    public Slice<CommunitySearchDto.Response> findSearchCommunity(Pageable pageable, String search) {
        List<Community> results = queryFactory
                .select(community)
                .from(community)
                .join(community.user, user).fetchJoin()
                .where(
                        searchByLike(search)
                )
                .groupBy(community.communityId)
                .orderBy(listSort(pageable))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        List<CommunitySearchDto.Response> contents = results.stream().map(community ->
                CommunitySearchDto.Response.of(community)
        ).collect(Collectors.toList());

        boolean hasNext = false;
        if (contents.size() > pageable.getPageSize()) {
            contents.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(contents, pageable, hasNext);

    }

    private BooleanExpression searchByLike(String searchQuery) {

        return community.title.like("%" + searchQuery + "%").or(community.content.like("%" + searchQuery + "%"));

    }

    private BooleanExpression findByMBTI(String mbti) {

        return mbti == null ? null : community.mbti.eq(MBTI.from(mbti));

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
