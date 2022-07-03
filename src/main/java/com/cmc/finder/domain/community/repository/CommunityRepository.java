package com.cmc.finder.domain.community.repository;

import com.cmc.finder.domain.community.entity.Community;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommunityRepository extends JpaRepository<Community, Long>, CommunityRepositoryCustom {

//    @Query("select dr from DebateAnswerReply dr " +
//            "join fetch dr.user u " +
//            "where dr.debateAnswer=:debateAnswer ")
//    List<DebateAnswerReply> findAllByDebateAnswerFetchUser(DebateAnswer debateAnswer);
//
//    @Query("select dr from DebateAnswerReply dr " +
//            "join fetch dr.user u " +
//            "where dr.debateReplyId=:debateReplyId ")
//    Optional<DebateAnswerReply> findByIdFetchUser(Long debateReplyId);
}
