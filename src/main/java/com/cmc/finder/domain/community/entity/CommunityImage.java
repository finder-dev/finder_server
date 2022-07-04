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
    private Long communityImageId;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "community_id", nullable = false)
    private Community community;

    @Column(name = "community_image_filename", nullable = false)
    private String imageName;

    @Column(name = "community_image_url", nullable = false)
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
