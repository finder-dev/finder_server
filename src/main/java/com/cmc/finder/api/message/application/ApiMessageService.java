package com.cmc.finder.api.message.application;

import com.cmc.finder.api.message.dto.*;
import com.cmc.finder.api.message.exception.BlockUserException;
import com.cmc.finder.domain.block.application.BlockService;
import com.cmc.finder.domain.block.entity.Block;
import com.cmc.finder.domain.community.exception.AlreadyBlockedUserException;
import com.cmc.finder.domain.message.application.MessageService;
import com.cmc.finder.domain.message.entity.Message;
import com.cmc.finder.domain.message.exception.CantSendMeException;
import com.cmc.finder.domain.model.Email;
import com.cmc.finder.domain.model.ServiceType;
import com.cmc.finder.domain.report.application.ReportService;
import com.cmc.finder.domain.report.entity.Report;
import com.cmc.finder.domain.report.exception.AlreadyReceivedReportException;
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

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ApiMessageService {

    private final MessageService messageService;
    private final UserService userService;
    private final FcmService fcmService;
    private final ReportService reportService;
    private final BlockService blockService;

    @Transactional
    public CreateMessageDto.Response sendMessage(CreateMessageDto.Request request, String email) {

        User from = userService.getUserByEmail(Email.of(email));
        User to = userService.getUserById(request.getToUserId());

        if (from.getUserId() == to.getUserId()) {
            throw new CantSendMeException(ErrorCode.CANT_SEND_ME_MESSAGE);
        }
        // 차단 확인
        if (blockService.isBlockUser(from, to)) {
            throw new BlockUserException(ErrorCode.BLOCK_USER);
        }

        Message fromMessage = Message.createMessage(from, to, from, request.getContent());
        Message toMessage = Message.createMessage(to, from, from, request.getContent());

        messageService.create(fromMessage);
        messageService.create(toMessage);

//        if (to.getIsActive()) {
//            fcmService.sendMessageTo(to.getFcmToken(), message.getContent(), MESSAGE, ServiceType.MESSAGE.getValue());
//        }

        return CreateMessageDto.Response.of();

    }


    public Slice<GetConversationDto.Response> getMessageByOther(String email, GetConversationDto.Request request, Pageable pageable) {
        User me = userService.getUserByEmail(Email.of(email));
        User other = userService.getUserById(request.getUserId());

        Slice<Message> messages = messageService.getMessageByOwnerAndOther(me, other, pageable);

        List<GetConversationDto.Response> res = messages.stream()
                .map(GetConversationDto.Response::of).collect(Collectors.toList());

        return new SliceImpl<>(res, pageable, messages.hasNext());
    }


    @Transactional
    public ReportUserDto.Response reportUser(ReportUserDto.Request request, String email) {

        User reportUser = userService.getUserById(request.getReportUserId());
        User from = userService.getUserByEmail(Email.of(email));

        Report report = Report.createReport(ServiceType.MESSAGE, from, reportUser, null);

        if (reportService.alreadyReportedUser(report)) {
            throw new AlreadyReceivedReportException(ErrorCode.ALREADY_RECEIVED_REPORT);
        }

        reportService.create(report);
        return ReportUserDto.Response.of();

    }

    @Transactional
    public DeleteMessageDto.Response deleteMessage(DeleteMessageDto.Request request, String email) {
        User other = userService.getUserById(request.getDeleteMsgUserId());
        User owner = userService.getUserByEmail(Email.of(email));

        messageService.deleteMessage(other, owner);
        return DeleteMessageDto.Response.of();

    }

    @Transactional
    public BlockUserDto.Response blockUser(BlockUserDto.Request request, String email) {
        User from = userService.getUserByEmail(Email.of(email));
        User to = userService.getUserById(request.getBlockUserId());

        if (blockService.alreadyBlockedUser(from, to)) {
            throw new AlreadyBlockedUserException(ErrorCode.ALREADY_BLOCKED_USER);
        }

        Block block = Block.createBlock(from, to);
        blockService.create(block);

        return BlockUserDto.Response.of();
    }
}
