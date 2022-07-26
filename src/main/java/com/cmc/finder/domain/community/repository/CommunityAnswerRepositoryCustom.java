package com.cmc.finder.domain.community.repository;

import com.cmc.finder.domain.community.entity.Community;
import com.cmc.finder.domain.community.entity.CommunityAnswer;
import com.cmc.finder.domain.user.entity.User;

import java.util.List;

public interface CommunityAnswerRepositoryCustom   {

    List<CommunityAnswer> findAllByCommunityFetchUser(Community community, User user);

}
