package com.cmc.finder.domain.block.entity;

import com.cmc.finder.domain.base.BaseTimeEntity;
import com.cmc.finder.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "block")
@Getter
@NoArgsConstructor
public class Block extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long blockId;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "from_id", nullable = false)
    private User from;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "to_id", nullable = false)
    private User to;


    @Builder
    public Block(User from, User to) {
        this.from = from;
        this.to = to;
    }

    public static Block createBlock(User from, User to) {

        return Block.builder()
                .from(from)
                .to(to)
                .build();
    }

}
