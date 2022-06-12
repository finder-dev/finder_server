package com.cmc.finder.api.qna.qustion.repository;

import com.cmc.finder.api.qna.qustion.dto.QQuestionSimpleDto;
import com.cmc.finder.api.qna.qustion.dto.QuestionSimpleDto;
import com.cmc.finder.domain.question.entity.QQuestion;
import com.cmc.finder.domain.question.entity.QQuestionImage;
import com.cmc.finder.domain.user.entity.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

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

        //TODO answercount 계산

        List<QuestionSimpleDto> results = queryFactory
                .select(
                        new QQuestionSimpleDto(
                                question.title,
                                question.content,
                                questionImage.imageUrl,
                                question.user.nickname,
                                question.user.mbti,
//                                Integer.getInteger("1"),
                                question.createTime
                        )
                )
                .from(question)
                .from(questionImage)
                .join(question.user, user)
                .where(questionImage.isRep.eq(Boolean.TRUE).and(questionImage.question.questionId.eq(question.questionId)))
//                .join(question.user, user).fetchJoin()
//                .where(
//                        qItem.stockNumber.ne(0),
//                        qItem.itemSellStatus.ne(ItemSellStatus.SOLD_OUT),
//                        qItemImage.isRepImg.eq(true),
//                        searchByLike(itemSearchDto.getSearchQuery())
//                )
                .orderBy(question.createTime.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        int totalSize = queryFactory
                .selectFrom(question)
//                .where(
//                        qItem.stockNumber.ne(0),
//                        qItem.itemSellStatus.ne(ItemSellStatus.SOLD_OUT),
//                        searchByLike(itemSearchDto.getSearchQuery())
//                )
                .fetch().size();

        return new PageImpl<>(results, pageable, totalSize);


    }
}
