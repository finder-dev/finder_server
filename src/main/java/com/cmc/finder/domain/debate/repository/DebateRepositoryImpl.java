package com.cmc.finder.domain.debate.repository;

import com.cmc.finder.api.debate.dto.DebateSimpleDto;
import com.cmc.finder.domain.debate.constant.DebateState;
import com.cmc.finder.domain.debate.entity.Debate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

import static com.cmc.finder.domain.debate.entity.QDebate.debate;
import static com.cmc.finder.domain.debate.entity.QDebater.debater;


@Repository
public class DebateRepositoryImpl implements DebateRepositoryCustom {

    private JPAQueryFactory queryFactory;

    public DebateRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<DebateSimpleDto.Response> findDebateSimpleDto(DebateState state, Pageable pageable) {

        List<Debate> results = queryFactory
                .select(debate)
                .from(debate)
//                .join(debate.debaters, debater).fetchJoin()
                .where(
                        searchByState(state)
                )
                .orderBy(debate.createTime.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        int totalSize = queryFactory
                .selectFrom(debater)
                .where(
                        searchByState(state)
                )
                .fetch().size();

        List<DebateSimpleDto.Response> debateSimpleDtos = results.stream().map(debate ->
                        DebateSimpleDto.Response.of(debate)
                ).collect(Collectors.toList());

        return new PageImpl<>(debateSimpleDtos, pageable, totalSize);
    }

    private BooleanExpression searchByState(DebateState state) {

        return debate.state.eq(state);
//        return state != null ? debate.state.eq(state) : debate.state.eq(DebateState.PROCEEDING);

    }




}
