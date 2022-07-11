package com.cmc.finder.domain.community.entity;

import com.cmc.finder.domain.base.BaseTimeEntity;
import com.cmc.finder.domain.model.MBTI;
import com.cmc.finder.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "community")
@Getter
@NoArgsConstructor
public class Community extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long communityId;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(length = 500, nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private MBTI mbti;

    @Column(nullable = false)
    private Boolean isQuestion;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(
            mappedBy = "community",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<CommunityImage> communityImages = new ArrayList<>();

    @OneToMany(
            mappedBy = "community",
            cascade = CascadeType.ALL
    )
    private List<CommunityAnswer> communityAnswers = new ArrayList<>();

    @OneToMany(
            mappedBy = "community",
            cascade = CascadeType.ALL
    )
    private List<Like> likeList = new ArrayList<>();

    @OneToMany(
            mappedBy = "community",
            cascade = CascadeType.ALL
    )
    private List<SaveCommunity> saveCommunityList = new ArrayList<>();

    public void addSaveCommunity(SaveCommunity saveCommunity) {
        saveCommunityList.add(saveCommunity);
        saveCommunity.setCommunity(this);
    }

    public void addLike(Like like) {
        likeList.add(like);
        like.setCommunity(this);
    }

    public void addCommunityImage(CommunityImage communityImage) {
        communityImages.add(communityImage);
        communityImage.setCommunity(this);
    }

    public void addAnswer(CommunityAnswer answer) {
        communityAnswers.add(answer);
        answer.setCommunity(this);
    }

    @Builder
    public Community(String title, String content, Boolean isQuestion, MBTI mbti, User user) {
        this.title = title;
        this.content = content;
        this.mbti = mbti;
        this.isQuestion = isQuestion;
        this.user = user;
    }

    public static Community createCommunity(Community community, User user) {
        return Community.builder()
                .title(community.title)
                .content(community.content)
                .mbti(community.mbti)
                .isQuestion(community.isQuestion)
                .user(user)
                .build();
    }

    public void updateCommunity(Community updateCommunity) {

        this.title = updateCommunity.getTitle();
        this.content = updateCommunity.getContent();
        this.mbti = updateCommunity.getMbti();

    }

    public void deleteCommunityImage(CommunityImage communityImage) {
        this.communityImages.remove(communityImage);
    }
}
