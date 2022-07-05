package com.cmc.finder.domain.community.repository;

import com.cmc.finder.domain.community.entity.Community;
import com.cmc.finder.domain.community.entity.SaveCommunity;
import com.cmc.finder.domain.qna.question.entity.FavoriteQuestion;
import com.cmc.finder.domain.qna.question.entity.Question;
import com.cmc.finder.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SaveCommunityRepository extends JpaRepository<SaveCommunity, Long> {

    Optional<SaveCommunity> findByCommunityAndUser(Community community, User user);

    Boolean existsByCommunityAndUser(Community community, User user);

    @Query("select sc "+
            "from SaveCommunity sc "+
//            "join fetch sc.community c " +
            "where sc.user=:user " +
            "order by sc.saveCommunityId desc ")
    Page<SaveCommunity> findAllByUserFetchCommunity(User user, Pageable pageable);

}
