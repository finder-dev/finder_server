package com.cmc.finder.domain.qna.answer.repostiory;

import com.cmc.finder.domain.qna.answer.entity.Answer;
import com.cmc.finder.domain.qna.answer.entity.AnswerReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface AnswerReplyRepository extends JpaRepository<AnswerReply, Long> {

    @Query("select r from AnswerReply r " +
            "join fetch r.user u " +
            "where r.answer=:answer ")
    List<AnswerReply> findAllByAnswerFetchUser(Answer answer);

    @Query("select r from AnswerReply r " +
            "join fetch r.user u " +
            "where r.replyId=:replyId ")
    Optional<AnswerReply> findByIdFetchUser(Long replyId);
}
