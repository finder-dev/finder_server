package com.cmc.finder.domain.community.repository;

import com.cmc.finder.domain.community.entity.Community;
import com.cmc.finder.domain.user.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface CommunityRepository extends JpaRepository<Community, Long>, CommunityRepositoryCustom {


    @Query("select c from Community c " +
            "join fetch c.user cu " +
            "where c.communityId=:communityId ")
    Optional<Community> findByCommunityIdFetchUser(Long communityId);

    Slice<Community> findAllByUserOrderByCommunityIdDesc(User user, Pageable pageable);

    @Query("select c " +
            "from Community c " +
            "join c.communityAnswers ca " +
            "where ca.user=:user " +
            "group by c.communityId " +
            "order by c.communityId desc ")
    Slice<Community> findAllByCommentUserFetchUser(User user, Pageable pageable);

}
