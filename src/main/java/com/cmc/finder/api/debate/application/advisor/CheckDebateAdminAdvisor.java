package com.cmc.finder.api.debate.application.advisor;

import com.cmc.finder.domain.debate.application.DebateAnswerService;
import com.cmc.finder.domain.debate.entity.DebateAnswer;
import com.cmc.finder.domain.model.Email;
import com.cmc.finder.domain.user.entity.User;
import com.cmc.finder.domain.user.application.UserService;
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
public class CheckDebateAdminAdvisor {

    private final UserService userService;
    private final DebateAnswerService debateAnswerService;

    @Before("@annotation(com.cmc.finder.global.advice.CheckDebateAdmin)")
    public void checkAdminCommunityUser(JoinPoint joinPoint) {

        Object[] args = joinPoint.getArgs();
        Long answerId = (Long) args[0];
        String email = (String) args[1];
        User checkUser = userService.getUserByEmail(Email.of(email));

        DebateAnswer debateAnswer = debateAnswerService.getDebateAnswerFetchUser(answerId);

        // 유저 검증
        if (checkUser != debateAnswer.getUser()) {
            throw new AuthenticationException(ErrorCode.DEBATE_ANSWER_USER_NOT_WRITER);
        }


    }
}
