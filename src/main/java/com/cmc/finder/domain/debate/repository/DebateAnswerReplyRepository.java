package com.cmc.finder.domain.debate.repository;

import com.cmc.finder.domain.debate.entity.DebateAnswer;
import com.cmc.finder.domain.debate.entity.DebateAnswerReply;
import com.cmc.finder.domain.qna.answer.entity.AnswerReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface DebateAnswerReplyRepository extends JpaRepository<DebateAnswerReply, Long> {

    @Query("select dr from DebateAnswerReply dr " +
            "join fetch dr.user u " +
            "where dr.debateAnswer=:debateAnswer ")
    List<DebateAnswerReply> findAllByDebateAnswerFetchUser(DebateAnswer debateAnswer);

    @Query("select dr from DebateAnswerReply dr " +
            "join fetch dr.user u " +
            "where dr.debateReplyId=:debateReplyId ")
    Optional<DebateAnswerReply> findByIdFetchUser(Long debateReplyId);
}
