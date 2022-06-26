package com.cmc.finder.domain.debate.entity;

import com.cmc.finder.domain.base.BaseTimeEntity;
import com.cmc.finder.domain.debate.constant.DebateState;
import com.cmc.finder.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "debate_answer")
@Getter
@NoArgsConstructor
public class DebateAnswer extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long debateAnswerId;

    @Lob
    @Column(nullable = false)
    private String content;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "debate_id", nullable = false)
    private Debate debate;


    @Builder
    public DebateAnswer(String content, User user, Debate debate) {
        this.content = content;
        this.user = user;
        this.debate = debate;
    }

    public static DebateAnswer createDebateAnswer(User user, Debate debate, DebateAnswer debateAnswer) {
        return DebateAnswer.builder()
                .content(debateAnswer.content)
                .user(user)
                .debate(debate)
                .build();
    }

    public void setDebate(Debate debate) {
        this.debate = debate;
    }
}