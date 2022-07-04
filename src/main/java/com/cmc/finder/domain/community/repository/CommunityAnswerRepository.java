package com.cmc.finder.domain.community.repository;

import com.cmc.finder.domain.community.entity.CommunityAnswer;
import com.cmc.finder.domain.qna.answer.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommunityAnswerRepository extends JpaRepository<CommunityAnswer, Long> {

    @Query("select ca from CommunityAnswer ca " +
            "join fetch ca.user u " +
            "where ca.community.communityId=:communityId ")
    List<CommunityAnswer> findAllByCommunityIdFetchUser(Long communityId);

    @Query("select ca from CommunityAnswer ca " +
            "join fetch ca.user u " +
            "where ca.communityAnswerId=:communityAnswerId ")
    Optional<CommunityAnswer> findByIdFetchUser(Long communityAnswerId);
}
