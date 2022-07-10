package com.cmc.finder.domain.community.repository;

import com.cmc.finder.domain.community.entity.Community;
import com.cmc.finder.domain.community.entity.CommunityAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommunityAnswerRepository extends JpaRepository<CommunityAnswer, Long> {

    @Query("select ca from CommunityAnswer ca " +
            "join fetch ca.user u " +
            "where ca.community=:community and ca.parent is null "+
            "order by ca.communityAnswerId desc ")
    List<CommunityAnswer> findAllByCommunityFetchUser(Community community);

    @Query("select ca from CommunityAnswer ca " +
            "join fetch ca.user u " +
            "where ca.communityAnswerId=:communityAnswerId ")
    Optional<CommunityAnswer> findByIdFetchUser(Long communityAnswerId);



}
