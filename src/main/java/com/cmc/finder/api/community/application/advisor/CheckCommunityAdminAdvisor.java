package com.cmc.finder.api.community.application.advisor;

import com.cmc.finder.domain.community.application.CommunityAnswerService;
import com.cmc.finder.domain.community.application.CommunityService;
import com.cmc.finder.domain.community.entity.Community;
import com.cmc.finder.domain.community.entity.CommunityAnswer;
import com.cmc.finder.domain.model.Email;
import com.cmc.finder.domain.user.entity.User;
import com.cmc.finder.domain.user.service.UserService;
import com.cmc.finder.global.error.exception.AuthenticationException;
import com.cmc.finder.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class CheckCommunityAdminAdvisor {

    private final UserService userService;
    private final CommunityService communityService;
    private final CommunityAnswerService communityAnswerService;

    @Before("@annotation(com.cmc.finder.global.aspect.CheckCommunityAdmin)")
    public void checkAdminCommunityUser(JoinPoint joinPoint) {

        Object[] args = joinPoint.getArgs();
        String email = (String) args[1];
        User checkUser = userService.getUserByEmail(Email.of(email));

        if (joinPoint.getSignature().getName().contains("Community")) {

            Long communityId = (Long) args[0];
            Community community = communityService.getCommunityFetchUser(communityId);

            // 유저 검증
            if (checkUser != community.getUser()) {
                throw new AuthenticationException(ErrorCode.COMMUNITY_USER_NOT_WRITER);
            }

        } else {

            Long answerId = (Long) args[0];
            CommunityAnswer communityAnswer = communityAnswerService.getCommunityAnswerFetchUser(answerId);
            // 유저 검증
            if (checkUser != communityAnswer.getUser()) {

                throw new AuthenticationException(ErrorCode.ANSWER_USER_NOT_WRITER);
            }

        }

    }
}
