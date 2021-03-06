package com.cmc.finder.domain.qna.answer.service;

import com.cmc.finder.domain.qna.answer.entity.Answer;
import com.cmc.finder.domain.qna.answer.entity.AnswerReply;
import com.cmc.finder.domain.qna.answer.repostiory.AnswerReplyRepository;
import com.cmc.finder.global.error.exception.EntityNotFoundException;
import com.cmc.finder.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnswerReplyService {

    private final AnswerReplyRepository answerReplyRepository;

    @Transactional
    public AnswerReply create(AnswerReply answerReply) {
        return answerReplyRepository.save(answerReply);
    }

    public AnswerReply getReply(Long replyId) {
        return answerReplyRepository.findById(replyId)
                .orElseThrow(()-> new EntityNotFoundException(ErrorCode.REPLY_NOT_FOUND));

    }


    public AnswerReply getReplyFetchUser(Long replyId) {
        return answerReplyRepository.findByIdFetchUser(replyId)
                .orElseThrow(()-> new EntityNotFoundException(ErrorCode.REPLY_NOT_FOUND));
    }

    public List<AnswerReply> getReplyByAnswerFetchUser(Answer answer) {
        return answerReplyRepository.findAllByAnswerFetchUser(answer);
    }

    @Transactional
    public void deleteReply(AnswerReply answerReply) {
        answerReplyRepository.delete(answerReply);
    }

    @Transactional
    public AnswerReply updateReply(AnswerReply answerReply, AnswerReply updateAnswerReply) {
        answerReply.updateReply(updateAnswerReply);
        return answerReply;
    }
}
