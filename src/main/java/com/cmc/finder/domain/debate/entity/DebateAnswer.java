package com.cmc.finder.domain.debate.entity;

import com.cmc.finder.domain.base.BaseTimeEntity;
import com.cmc.finder.domain.community.entity.CommunityAnswer;
import com.cmc.finder.domain.debate.constant.DebateState;
import com.cmc.finder.domain.qna.answer.entity.AnswerReply;
import com.cmc.finder.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "debate_answer")
@Getter
@NoArgsConstructor
public class DebateAnswer extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DEBATE_ANSWER_ID")
    private Long Id;

    @Lob
    @Column(nullable = false)
    private String content;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "DEBATE_ID", nullable = false)
    private Debate debate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    private DebateAnswer parent;

    @OneToMany(mappedBy = "parent", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<DebateAnswer> replies = new ArrayList<>();

    public void updateDebateAnswer(DebateAnswer updateDebateAnswer) {
        this.content = updateDebateAnswer.content;
    }

    public void addReply(DebateAnswer debateAnswer) {
        this.parent.addReply(debateAnswer);
    }

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

    public void setParent(DebateAnswer debateAnswer) {
        this.parent = debateAnswer;
    }


}
