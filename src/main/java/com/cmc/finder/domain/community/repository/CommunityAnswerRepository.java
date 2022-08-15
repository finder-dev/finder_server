package com.cmc.finder.domain.community.repository;

import com.cmc.finder.domain.community.entity.CommunityAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CommunityAnswerRepository extends JpaRepository<CommunityAnswer, Long>, CommunityAnswerRepositoryCustom {

    @Query("select ca from CommunityAnswer ca " +
            "join fetch ca.user u " +
            "where ca.iD=:communityAnswerId ")
    Optional<CommunityAnswer> findByIdFetchUser(Long communityAnswerId);


}
