package com.cmc.finder.domain.block.application;

import com.cmc.finder.domain.block.entity.Block;
import com.cmc.finder.domain.block.repository.BlockRepository;
import com.cmc.finder.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BlockService {

    private final BlockRepository blockRepository;

    @Transactional
    public void create(Block block) {
        blockRepository.save(block);
    }

    public Boolean isBlockUser(User from, User to) {
        return blockRepository.existsByFromOrTo(from, to);
    }

    public boolean alreadyBlockedUser(User from, User to) {
        return blockRepository.existsByFromAndTo(from, to);
    }
}
