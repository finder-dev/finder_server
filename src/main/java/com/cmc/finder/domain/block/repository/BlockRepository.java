package com.cmc.finder.domain.block.repository;

import com.cmc.finder.domain.block.entity.Block;
import com.cmc.finder.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BlockRepository extends JpaRepository<Block, Long> {

    @Query("select count (b) > 0 " +
            "from Block b " +
            "where ((b.from=:from and b.to=:to) or (b.from=:to and b.to=:from))")
    Boolean existsByFromOrTo(User from, User to);

    Boolean existsByFromAndTo(User from, User to);

    List<Block> findAllByFrom(User from);

}
