package com.cmc.finder.domain.debate.repository;

import com.cmc.finder.domain.block.entity.Block;
import com.cmc.finder.domain.debate.entity.Debate;
import com.cmc.finder.domain.debate.entity.DebateAnswer;
import com.cmc.finder.domain.model.ServiceType;
import com.cmc.finder.domain.report.entity.Report;
import com.cmc.finder.domain.user.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

import static com.cmc.finder.domain.block.entity.QBlock.block;
import static com.cmc.finder.domain.debate.entity.QDebateAnswer.debateAnswer;
import static com.cmc.finder.domain.report.entity.QReport.report;
import static com.cmc.finder.domain.user.entity.QUser.user;

@Repository
public class DebateAnswerRepositoryImpl implements DebateAnswerRepositoryCustom {

    private JPAQueryFactory queryFactory;

    public DebateAnswerRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<DebateAnswer> findAllByDebateFetchUser(Debate debate, User curUser) {

        return queryFactory
                .selectFrom(debateAnswer)
                .join(debateAnswer.user, user).fetchJoin()
                .join(debateAnswer.replies).fetchJoin()
                .where(
                        debateAnswer.debate.eq(debate),
                        debateAnswer.parent.isNull(),
                        debateAnswer.debateAnswerId.notIn(getReportsByUser(curUser)),
                        debateAnswer.user.notIn(getBlockUser(curUser))
                )
                .orderBy(debateAnswer.debateAnswerId.desc())
                .fetch();
    }

    private List<Long> getReportsByUser(User user) {

        return queryFactory
                .selectFrom(report)
                .where(report.serviceType.eq(ServiceType.DEBATE_ANSWER), report.from.eq(user))
                .fetch().stream().map(Report::getServiceId).collect(Collectors.toList());

    }

    private List<User> getBlockUser(User user) {

        return queryFactory
                .selectFrom(block)
                .where(block.from.eq(user))
                .fetch().stream().map(Block::getTo).collect(Collectors.toList());

    }


}
