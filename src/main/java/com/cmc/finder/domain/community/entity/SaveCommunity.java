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
    @Column(name = "SAVE_COMMUNITY_ID")
    private Long Id;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "COMMUNITY_ID", nullable = false)
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
