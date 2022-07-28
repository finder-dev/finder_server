package com.cmc.finder.api.user.application;

import com.cmc.finder.api.user.dto.BlockUserDto;
import com.cmc.finder.domain.block.application.BlockService;
import com.cmc.finder.domain.block.entity.Block;
import com.cmc.finder.domain.community.exception.AlreadyBlockedUserException;
import com.cmc.finder.domain.model.Email;
import com.cmc.finder.domain.user.entity.User;
import com.cmc.finder.domain.user.application.UserService;
import com.cmc.finder.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserBlockService {

    private final UserService userService;
    private final BlockService blockService;

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
