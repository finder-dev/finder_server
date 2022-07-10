package com.cmc.finder.domain.community.entity;

import com.cmc.finder.domain.base.BaseTimeEntity;
import com.cmc.finder.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "community_answer")
@Getter
@NoArgsConstructor
@ToString
public class CommunityAnswer extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long communityAnswerId;

    @Lob
    @Column(length = 300, nullable = false)
    private String content;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private CommunityAnswer parent;

    @OneToMany(mappedBy = "parent", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<CommunityAnswer> replies = new ArrayList<>();

    public void addReply(CommunityAnswer communityAnswer) {
        this.parent.addReply(communityAnswer);
    }


    @Builder
    public CommunityAnswer(String content, User user, Community community) {
        this.content = content;
        this.user = user;
        this.community = community;
    }

    public static CommunityAnswer createCommunityAnswer(User user, Community community, CommunityAnswer communityAnswer) {
        return CommunityAnswer.builder()
                .content(communityAnswer.content)
                .user(user)
                .community(community)
                .build();
    }

    public void setCommunity(Community community) {
        this.community = community;
    }


    public void updateCommunityAnswer(CommunityAnswer updateCommunityAnswer) {

        this.content = updateCommunityAnswer.content;
    }

    public void setParent(CommunityAnswer communityAnswer) {
        this.parent = communityAnswer;
    }
}
