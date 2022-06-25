package com.cmc.finder.api.debate.repository;

import com.cmc.finder.api.debate.dto.DebateSimpleDto;
import com.cmc.finder.domain.debate.constant.DebateState;
import com.cmc.finder.domain.debate.entity.Debate;
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
    public Page<DebateSimpleDto.Response> findDebateSimpleDto(Pageable pageable) {

        List<Debate> results = queryFactory
                .select(debate)
                .from(debate)
//                .join(debate.debaters, debater).fetchJoin()
                .where(
                        debate.state.eq(DebateState.PROCEEDING)
                )
                .orderBy(debate.createTime.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        int totalSize = queryFactory
                .selectFrom(debater)
                .where(
                        debate.state.eq(DebateState.PROCEEDING)
                )
                .fetch().size();

        List<DebateSimpleDto.Response> debateSimpleDtos = results.stream().map(debate ->
                        DebateSimpleDto.Response.of(debate)
                ).collect(Collectors.toList());

        return new PageImpl<>(debateSimpleDtos, pageable, totalSize);
    }




}
