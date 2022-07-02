package com.cmc.finder.domain.qna.answer.repostiory;

import com.cmc.finder.domain.qna.answer.entity.Answer;
import com.cmc.finder.domain.qna.answer.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @Query("select r from Reply r " +
            "join fetch r.user u " +
            "where r.answer=:answer ")
    List<Reply> findAllByAnswerFetchUser(Answer answer);

    @Query("select r from Reply r " +
            "join fetch r.user u " +
            "where r.replyId=:replyId ")
    Optional<Reply> findByIdFetchUser(Long replyId);
}