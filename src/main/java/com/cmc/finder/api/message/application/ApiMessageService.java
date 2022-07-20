package com.cmc.finder.api.message.application;

import com.cmc.finder.api.message.dto.CreateMessageDto;
import com.cmc.finder.api.message.dto.GetConversationDto;
import com.cmc.finder.domain.message.application.MessageService;
import com.cmc.finder.domain.message.entity.Message;
import com.cmc.finder.domain.message.exception.CantSendMeException;
import com.cmc.finder.domain.model.Email;
import com.cmc.finder.domain.model.ServiceType;
import com.cmc.finder.domain.user.entity.User;
import com.cmc.finder.domain.user.service.UserService;
import com.cmc.finder.global.error.exception.ErrorCode;
import com.cmc.finder.infra.notification.FcmService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.cmc.finder.global.util.Constants.MESSAGE;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ApiMessageService {

    private final MessageService messageService;
    private final UserService userService;
    private final FcmService fcmService;

    @Transactional
    public CreateMessageDto.Response sendMessage(CreateMessageDto.Request request, String email) {

        User from = userService.getUserByEmail(Email.of(email));
        User to = userService.getUserById(request.getToUserId());

        if (from.getUserId() == to.getUserId()) {
            throw new CantSendMeException(ErrorCode.CANT_SEND_ME_MESSAGE);
        }

        Message message = Message.createMessage(from, to, request.getContent());
        messageService.create(message);

//        if (to.getIsActive()) {
//            fcmService.sendMessageTo(to.getFcmToken(), message.getContent(), MESSAGE, ServiceType.MESSAGE.getValue());
//        }

        return CreateMessageDto.Response.of();

    }


    public Slice<GetConversationDto.Response> getMessageByToUser(String email, GetConversationDto.Request request, Pageable pageable) {
        User me = userService.getUserByEmail(Email.of(email));
        User other = userService.getUserById(request.getUserId());

        Slice<Message> messages = messageService.getMessageByFromOrTo(me, other, pageable);

        List<GetConversationDto.Response> res = messages.stream().map(GetConversationDto.Response::from).collect(Collectors.toList());

        return new SliceImpl<>(res, pageable, messages.hasNext());
    }


}
