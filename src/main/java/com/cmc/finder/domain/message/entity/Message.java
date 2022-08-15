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
    @Column(name = "MESSAGE_ID")
    private Long Id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "OWNER_ID", nullable = false)
    private User owner;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "OTHER_ID", nullable = false)
    private User other;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "FROM_ID", nullable = false)
    private User from;


    @Builder
    public Message(User owner, User other, User from, String content) {
        this.owner = owner;
        this.other = other;
        this.from = from;
        this.content = content;
    }

    public static Message createMessage(User owner, User other, User from, String content) {

        return Message.builder()
                .owner(owner)
                .other(other)
                .from(from)
                .content(content)
                .build();
    }


}
