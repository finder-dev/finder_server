package com.cmc.finder.domain.community.entity;

import com.cmc.finder.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "save_community")
@Getter
@NoArgsConstructor
public class SaveCommunity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long saveCommunityId;

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
    public SaveCommunity(Community community, User user) {
        this.community = community;
        this.user = user;
    }

    public static SaveCommunity createSaveCommunity(Community community, User user) {
        return SaveCommunity.builder()
                .community(community)
                .user(user)
                .build();
    }

    public void setCommunity(Community community) {
        this.community = community;
    }

}
