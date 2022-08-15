package com.cmc.finder.domain.qna.answer.entity;

import com.cmc.finder.domain.base.BaseTimeEntity;
import com.cmc.finder.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "answer_reply")
@Getter
@NoArgsConstructor
public class AnswerReply extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REPLY_ID")
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
    @JoinColumn(name = "ANSWER_ID", nullable = false)
    private Answer answer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    private AnswerReply parent;

    @OneToMany(mappedBy = "parent", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<AnswerReply> replies = new ArrayList<>();

    public void addReply(AnswerReply answerReply) {
        this.parent.addReply(answerReply);
    }

    @Builder
    public AnswerReply(String content, User user, Answer answer) {
        this.content = content;
        this.user = user;
        this.answer = answer;
    }

    public static AnswerReply createReply(AnswerReply answerReply, User user, Answer answer) {
        return AnswerReply.builder()
                .content(answerReply.content)
                .user(user)
                .answer(answer)
                .build();
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }


    public void updateReply(AnswerReply savedAnswerReply) {
        this.content = savedAnswerReply.getContent();
    }
}
