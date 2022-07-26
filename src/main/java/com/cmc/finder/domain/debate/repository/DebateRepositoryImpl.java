package com.cmc.finder.domain.debate.repository;

import com.cmc.finder.api.debate.dto.DebateSimpleDto;
import com.cmc.finder.domain.block.entity.Block;
import com.cmc.finder.domain.debate.constant.DebateState;
import com.cmc.finder.domain.debate.entity.Debate;
import com.cmc.finder.domain.model.ServiceType;
import com.cmc.finder.domain.report.entity.Report;
import com.cmc.finder.domain.user.entity.User;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

import static com.cmc.finder.domain.block.entity.QBlock.block;
import static com.cmc.finder.domain.debate.entity.QDebate.debate;
import static com.cmc.finder.domain.debate.entity.QDebater.debater;
import static com.cmc.finder.domain.report.entity.QReport.report;


@Repository
public class DebateRepositoryImpl implements DebateRepositoryCustom {

    private JPAQueryFactory queryFactory;

    public DebateRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Slice<DebateSimpleDto.Response> findDebateSimpleDto(DebateState state, User curUser, Pageable pageable) {

        List<Debate> results = queryFactory
                .selectFrom(debate)
                .where(
                        searchByState(state),
                        debate.debateId.notIn(getReportsByUser(curUser)),
                        debate.writer.notIn(getBlockUser(curUser))
                )
                .orderBy(debate.debateId.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();


        List<DebateSimpleDto.Response> contents = results.stream().map(DebateSimpleDto.Response::from
        ).collect(Collectors.toList());

        boolean hasNext = false;
        if (contents.size() > pageable.getPageSize()) {
            contents.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(contents, pageable, hasNext);
    }

    @Override
    public List<Debate> findHotDebate(Pageable pageable, User user) {

        return queryFactory
                .selectFrom(debate)
                .join(debate.debaters, debater)
                .where(
                        searchByState(DebateState.PROCEEDING),
                        debate.debateId.notIn(getReportsByUser(user)),
                        debate.writer.notIn(getBlockUser(user))
                )
                .orderBy(debate.debaters.size().desc())
                .fetch();

    }

    private BooleanExpression searchByState(DebateState state) {

        return debate.state.eq(state);

    }

    private List<Long> getReportsByUser(User user) {

        return queryFactory
                .selectFrom(report)
                .where(report.serviceType.eq(ServiceType.DEBATE), report.from.userId.eq(user.getUserId()))
                .fetch().stream().map(Report::getServiceId).collect(Collectors.toList());

    }

    private List<User> getBlockUser(User user) {

        return queryFactory
                .selectFrom(block)
                .where(block.from.eq(user))
                .fetch().stream().map(Block::getTo).collect(Collectors.toList());

    }


}
