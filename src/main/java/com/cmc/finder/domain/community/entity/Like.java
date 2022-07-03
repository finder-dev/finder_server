package com.cmc.finder.domain.community.entity;

import com.cmc.finder.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "community_like")
@Getter
@NoArgsConstructor
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "community_id", nullable = false)
    private Community community;

    @Builder
    public Like(Community community, User user) {
        this.community = community;
        this.user = user;
    }

    public static Like createLike(Community community, User user) {
        return Like.builder()
                .community(community)
                .user(user)
                .build();
    }

    public void setCommunity(Community community) {
        this.community = community;
    }


}
