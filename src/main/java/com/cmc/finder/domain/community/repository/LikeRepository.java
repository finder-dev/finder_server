package com.cmc.finder.domain.community.repository;

import com.cmc.finder.domain.community.entity.Community;
import com.cmc.finder.domain.community.entity.Like;
import com.cmc.finder.domain.qna.answer.entity.Answer;
import com.cmc.finder.domain.qna.answer.entity.Helpful;
import com.cmc.finder.domain.qna.question.entity.Question;
import com.cmc.finder.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface LikeRepository extends JpaRepository<Like, Long> {

    Boolean existsByCommunityAndUser(Community community, User user);

    Optional<Like> findByCommunityAndUser(Community community, User user);

}
