package com.cmc.finder.domain.keyword.entity;

import com.cmc.finder.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "user_keyword")
@Getter
@NoArgsConstructor
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long keywordId;

    @Column(nullable = false, length = 8)
    private String keyword;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder
    public Keyword(String keyword, User user) {
        this.keyword = keyword;
        this.user = user;
    }

    public static Keyword createKeyword(String keyword, User user) {
        return Keyword.builder()
                .keyword(keyword)
                .user(user)
                .build();
    }
}
