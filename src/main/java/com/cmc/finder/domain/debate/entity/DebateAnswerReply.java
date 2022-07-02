package com.cmc.finder.domain.debate.entity;

import com.cmc.finder.domain.base.BaseTimeEntity;
import com.cmc.finder.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "debate_answer_reply")
@Getter
@NoArgsConstructor
public class DebateAnswerReply extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long debateReplyId;

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
    @JoinColumn(name = "debate_answer_id", nullable = false)
    private DebateAnswer debateAnswer;

    @Builder
    public DebateAnswerReply(String content, User user, DebateAnswer debateAnswer) {
        this.content = content;
        this.user = user;
        this.debateAnswer = debateAnswer;
    }

    public static DebateAnswerReply createDebateReply(DebateAnswerReply debateAnswerReply, User user, DebateAnswer debateAnswer) {
        return DebateAnswerReply.builder()
                .content(debateAnswerReply.content)
                .user(user)
                .debateAnswer(debateAnswer)
                .build();
    }

    public void setDebateAnswer(DebateAnswer debateAnswer) {
        this.debateAnswer = debateAnswer;
    }


    public void updateReply(DebateAnswerReply savedAnswerReply) {
        this.content = savedAnswerReply.getContent();
    }
}
