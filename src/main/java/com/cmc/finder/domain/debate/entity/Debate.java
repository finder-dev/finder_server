package com.cmc.finder.domain.debate.entity;

import com.cmc.finder.domain.base.BaseTimeEntity;
import com.cmc.finder.domain.debate.constant.DebateState;
import com.cmc.finder.domain.model.MBTI;
import com.cmc.finder.domain.question.entity.Question;
import com.cmc.finder.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "debate")
@Getter
@NoArgsConstructor
public class Debate extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long debateId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private DebateState state;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "user_id", nullable = false)
    private User writer;

    //TODO 글자수 제한
    @Column(nullable = false)
    private String optionA;

    @Column(nullable = false)
    private String optionB;

    @Builder
    public Debate(String title,User writer, String optionA, String optionB) {
        this.title = title;
        this.writer = writer;
        this.state = DebateState.PROCEEDING;
        this.optionA = optionA;
        this.optionB = optionB;
    }

    public static Debate createDebate(String title,User writer, String optionA, String optionB) {
        return Debate.builder()
                .title(title)
                .writer(writer)
                .optionA(optionA)
                .optionB(optionB)
                .build();
    }

}
