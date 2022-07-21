package com.cmc.finder.domain.message.entity;

import com.cmc.finder.domain.base.BaseTimeEntity;
import com.cmc.finder.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "message")
@Getter
@NoArgsConstructor
public class Message extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;


    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "other_id", nullable = false)
    private User other;

    @Column(nullable = false)
    private String content;

    @Builder
    public Message(User owner, User other, String content) {
        this.owner = owner;
        this.other = other;
        this.content = content;
    }

    public static Message createMessage(User owner, User other, String content) {

        return Message.builder()
                .owner(owner)
                .other(other)
                .content(content)
                .build();
    }


}
