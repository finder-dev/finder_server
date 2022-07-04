package com.cmc.finder.domain.community.repository;

import com.cmc.finder.domain.community.entity.CommunityImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommunityImageRepository extends JpaRepository<CommunityImage, Long> {


}
