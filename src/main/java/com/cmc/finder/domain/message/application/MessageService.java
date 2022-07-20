package com.cmc.finder.domain.message.application;

import com.cmc.finder.domain.message.entity.Message;
import com.cmc.finder.domain.message.repository.MessageRepository;
import com.cmc.finder.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MessageService {

    private final MessageRepository messageRepository;

    @Transactional
    public void create(Message message) {
        messageRepository.save(message);
    }

    public Slice<Message> getMessageByFrom(User from, Pageable pageable) {
        return messageRepository.findAllByFrom(from, pageable);
    }


    public Slice<Message> getMessageByFromOrTo(User from, User to, Pageable pageable) {

        return messageRepository.findAllByFromOrTo(from, to, pageable);
    }

    @Transactional
    public void deleteMessage(User deletedMessageUser, User from) {

        List<Message> deleteMessage = messageRepository.findAllByFromOrTo(deletedMessageUser, from);
        messageRepository.deleteAllInBatch(deleteMessage);

    }
}
