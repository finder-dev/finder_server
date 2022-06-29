package com.cmc.finder.api.user.service;

import com.cmc.finder.domain.debate.service.DebateService;
import com.cmc.finder.domain.qna.question.service.QuestionService;
import com.cmc.finder.domain.user.service.UserService;
import com.cmc.finder.domain.user.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserActivityService {

    private final UserService userService;
    private final UserValidator userValidator;
    private final QuestionService questionService;
    private final DebateService debateService;



}
