package com.cmc.finder.domain.debate.repository;

import com.cmc.finder.api.debate.dto.DebateSimpleDto;
import com.cmc.finder.domain.debate.constant.DebateState;
import com.cmc.finder.domain.debate.entity.Debate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

import static com.cmc.finder.domain.debate.entity.QDebate.debate;


@Repository
public class DebateRepositoryImpl implements DebateRepositoryCustom {

    private JPAQueryFactory queryFactory;

    public DebateRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Slice<DebateSimpleDto.Response> findDebateSimpleDto(DebateState state, Pageable pageable) {


        List<Debate> results = queryFactory
                .select(debate)
                .from(debate)
                .where(
                        searchByState(state)
                )
                .orderBy(debate.debateId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();


        List<DebateSimpleDto.Response> contents = results.stream().map(debate ->
                DebateSimpleDto.Response.from(debate)
        ).collect(Collectors.toList());

        boolean hasNext = false;
        if (contents.size() > pageable.getPageSize()) {
            contents.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(contents, pageable, hasNext);
    }

    private BooleanExpression searchByState(DebateState state) {

        return debate.state.eq(state);

    }


}
