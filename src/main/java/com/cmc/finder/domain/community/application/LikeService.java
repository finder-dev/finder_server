package com.cmc.finder.domain.community.application;

import com.cmc.finder.domain.community.entity.Community;
import com.cmc.finder.domain.community.entity.Like;
import com.cmc.finder.domain.community.exception.LikeNotFoundException;
import com.cmc.finder.domain.community.repository.LikeRepository;
import com.cmc.finder.domain.qna.answer.entity.Helpful;
import com.cmc.finder.domain.qna.answer.exception.HelpfulNotFoundException;
import com.cmc.finder.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeService {

    private final LikeRepository likeRepository;

    public Boolean existsUser(Community community, User user) {

        return likeRepository.existsByCommunityAndUser(community, user);

    }


    public void deleteLike(Community community, User user) {
        Like like = likeRepository.findByCommunityAndUser(community, user)
                .orElseThrow(LikeNotFoundException::new);

        likeRepository.delete(like);

    }

    public void addLike(Like like) {
        likeRepository.save(like);
    }
}
