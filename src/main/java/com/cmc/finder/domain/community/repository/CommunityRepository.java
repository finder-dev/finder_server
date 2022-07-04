package com.cmc.finder.domain.community.repository;

import com.cmc.finder.domain.community.entity.Community;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface CommunityRepository extends JpaRepository<Community, Long>, CommunityRepositoryCustom {

    @Query(value = "select c " +
            "from Community c " +
            "join c.communityAnswers ca " +
            "group by c.communityId " +
            "order by ca.size desc ")
    List<Community> findHotCommunity(Pageable pageable);


    @Query("select c from Community c " +
            "join fetch c.user cu " +
            "where c.communityId=:communityId ")
    Optional<Community> findByCommunityIdFetchUser(Long communityId);

}
