package com.cmc.finder.domain.community.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "community_image")
@Getter
@NoArgsConstructor
public class CommunityImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMUNITY_IMAGE_ID")
    private Long Id;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "COMMUNITY_ID", nullable = false)
    private Community community;

    @Column(name = "COMMUNITY_IMAGE_NAME", nullable = false)
    private String imageName;

    @Column(name = "COMMUNITY_IMAGE_URL", nullable = false)
    private String imageUrl;

    @Builder
    public CommunityImage(Community community, String imageName, String imageUrl) {
        this.community = community;
        this.imageName = imageName;
        this.imageUrl = imageUrl;
    }

    public static CommunityImage createCommunityImage(Community community, String imageName, String imageUrl) {
        return CommunityImage.builder()
                .community(community)
                .imageName(imageName)
                .imageUrl(imageUrl)
                .build();
    }

    public void setCommunity(Community community) {
        this.community = community;
    }

}
