package com.cmc.finder.domain.community.repository;

import com.cmc.finder.api.community.dto.CommunitySearchDto;
import com.cmc.finder.api.community.dto.CommunitySimpleDto;
import com.cmc.finder.domain.block.entity.Block;
import com.cmc.finder.domain.community.entity.Community;
import com.cmc.finder.domain.community.entity.CommunityAnswer;
import com.cmc.finder.domain.model.MBTI;
import com.cmc.finder.domain.model.ServiceType;
import com.cmc.finder.domain.report.entity.Report;
import com.cmc.finder.domain.user.entity.User;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

import static com.cmc.finder.domain.block.entity.QBlock.block;
import static com.cmc.finder.domain.community.entity.QCommunityAnswer.communityAnswer;
import static com.cmc.finder.domain.report.entity.QReport.report;
import static com.cmc.finder.domain.user.entity.QUser.user;

@Repository
public class CommunityAnswerRepositoryImpl implements CommunityAnswerRepositoryCustom {

    private JPAQueryFactory queryFactory;

    public CommunityAnswerRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<CommunityAnswer> findAllByCommunityFetchUser(Community community, User curUser) {


        return queryFactory
                .selectFrom(communityAnswer)
                .join(communityAnswer.user, user).fetchJoin()
                .join(communityAnswer.replies).fetchJoin()
                .where(
                        communityAnswer.community.eq(community),
                        communityAnswer.parent.isNull(),
                        communityAnswer.communityAnswerId.notIn(getReportsByUser(curUser)),
                        communityAnswer.user.notIn(getBlockUser(curUser))
                )
                .orderBy(communityAnswer.communityAnswerId.desc())
                .fetch();
    }

    private List<Long> getReportsByUser(User user) {

        return queryFactory
                .selectFrom(report)
                .where(report.serviceType.eq(ServiceType.COMMUNITY_ANSWER), report.from.eq(user))
                .fetch().stream().map(Report::getServiceId).collect(Collectors.toList());

    }

    private List<User> getBlockUser(User user) {

        return queryFactory
                .selectFrom(block)
                .where(block.from.eq(user))
                .fetch().stream().map(Block::getTo).collect(Collectors.toList());

    }


}
