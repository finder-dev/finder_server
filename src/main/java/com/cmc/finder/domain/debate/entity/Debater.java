package com.cmc.finder.domain.debate.entity;

import com.cmc.finder.domain.debate.constant.Option;
import com.cmc.finder.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "debater")
@Getter
@NoArgsConstructor
public class Debater {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long debaterId;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "debate_id", nullable = false)
    private Debate debate;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "options")
    private Option option;

    @Builder
    public Debater(Debate debate, User user, Option option) {
        this.debate = debate;
        this.user = user;
        this.option = option;
    }

    public static Debater createDebater(Debate debate, User user, Option option) {
        return Debater.builder()
                .debate(debate)
                .user(user)
                .option(option)
                .build();
    }

    public void updateOption(Option option) {
        this.option = option;
    }

    public void setDebate(Debate debate) {
        this.debate = debate;
    }
}
