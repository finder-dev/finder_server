package com.cmc.finder.api.user.application;

import com.cmc.finder.api.user.dto.GetSaveCommunityRes;
import com.cmc.finder.api.user.dto.GetUserActivityRes;
import com.cmc.finder.domain.community.application.CommunityService;
import com.cmc.finder.domain.community.application.SaveCommunityService;
import com.cmc.finder.domain.community.entity.Community;
import com.cmc.finder.domain.community.entity.SaveCommunity;
import com.cmc.finder.domain.model.Email;
import com.cmc.finder.domain.user.entity.User;
import com.cmc.finder.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserActivityService {

    private final UserService userService;
    private final SaveCommunityService saveCommunityService;
    private final CommunityService communityService;

    public Page<GetSaveCommunityRes> getSaveCommunity(String email, Pageable pageable) {

        User user = userService.getUserByEmail(Email.of(email));
        Page<SaveCommunity> saveCommunityFetchCommunity = saveCommunityService.getSaveCommunityFetchCommunity(user, pageable);

        List<GetSaveCommunityRes> res = saveCommunityFetchCommunity.stream().map(saveCommunity ->
                GetSaveCommunityRes.of(saveCommunity.getCommunity())).collect(Collectors.toList());

        return new PageImpl<>(res);

    }

    public Page<GetUserActivityRes> getCommunityByUser(String email, Pageable pageable) {

        User user = userService.getUserByEmail(Email.of(email));
        Page<Community> communityList = communityService.getCommunityByUser(user, pageable);

        List<GetUserActivityRes> res = communityList.stream().map(community -> GetUserActivityRes.of(community)).collect(Collectors.toList());
        return new PageImpl<>(res);

    }

    public Page<GetUserActivityRes> getCommunityByCommentUser(String email, Pageable pageable) {

        User user = userService.getUserByEmail(Email.of(email));
        Page<Community> communityList = communityService.getCommunityByCommentUser(user, pageable);

        List<GetUserActivityRes> res = communityList.stream().map(community -> GetUserActivityRes.of(community)).collect(Collectors.toList());
        return new PageImpl<>(res);

    }


}
