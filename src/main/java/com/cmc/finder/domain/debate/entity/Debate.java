package com.cmc.finder.domain.debate.entity;

import com.cmc.finder.domain.base.BaseTimeEntity;
import com.cmc.finder.domain.debate.constant.DebateState;
import com.cmc.finder.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "debate")
@Getter
@NoArgsConstructor
public class Debate extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DEBATE_ID")
    private Long Id;

    @Column(nullable = false, length = 50)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DebateState state;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "USER_ID", nullable = false)
    private User writer;

    @Column(name = "OPTION_A", nullable = false, length = 8)
    private String optionA;

    @Column(name = "OPTION_B", nullable = false, length = 8)
    private String optionB;

    @OneToMany(
            mappedBy = "debate",
            cascade = CascadeType.ALL
    )
    private List<Debater> debaters = new ArrayList<>();

    @OneToMany(
            mappedBy = "debate",
            cascade = CascadeType.ALL
    )
    private List<DebateAnswer> debateAnswers = new ArrayList<>();

    public void addDebater(Debater debater) {
        debaters.add(debater);
        debater.setDebate(this);
    }

    public void addDebateAnswer(DebateAnswer debateAnswer) {
        debateAnswers.add(debateAnswer);
        debateAnswer.setDebate(this);
    }

    @Builder
    public Debate(String title,User writer, String optionA, String optionB) {
        this.title = title;
        this.writer = writer;
        this.state = DebateState.PROCEEDING;
        this.optionA = optionA;
        this.optionB = optionB;
    }

    public static Debate createDebate(Debate debate,User writer) {
        return Debate.builder()
                .title(debate.getTitle())
                .writer(writer)
                .optionA(debate.getOptionA())
                .optionB(debate.getOptionB())
                .build();
    }

    public void updateDebateState(DebateState state) {
        this.state = state;
    }
}
