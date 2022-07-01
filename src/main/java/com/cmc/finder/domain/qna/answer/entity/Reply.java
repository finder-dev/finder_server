package com.cmc.finder.domain.qna.answer.entity;

import com.cmc.finder.domain.base.BaseTimeEntity;
import com.cmc.finder.domain.qna.question.entity.Question;
import com.cmc.finder.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "reply")
@Getter
@NoArgsConstructor
public class Reply extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long replyId;

    //TODO 글자수 제한
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
    @JoinColumn(name = "answer_id", nullable = false)
    private Answer answer;

    @Builder
    public Reply(String content, User user, Answer answer) {
        this.content = content;
        this.user = user;
        this.answer = answer;
    }

    public static Reply createReply(Reply reply, User user, Answer answer) {
        return Reply.builder()
                .content(reply.content)
                .user(user)
                .answer(answer)
                .build();
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }


    public void updateReply(Reply savedReply) {
        this.content = savedReply.getContent();
    }
}
