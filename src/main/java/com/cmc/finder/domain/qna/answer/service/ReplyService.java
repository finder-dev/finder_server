package com.cmc.finder.domain.qna.answer.service;

import com.cmc.finder.domain.qna.answer.entity.Answer;
import com.cmc.finder.domain.qna.answer.entity.Reply;
import com.cmc.finder.domain.qna.answer.repostiory.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReplyService {

    private final ReplyRepository replyRepository;

    @Transactional
    public Reply create(Reply reply) {
        return replyRepository.save(reply);
    }

    public List<Reply> getReplyByAnswerFetchUser(Long answerId) {
        return replyRepository.findAllByAnswerFetchUser(answerId);

    }

}
