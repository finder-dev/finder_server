package com.cmc.finder.api.user.application;

import com.cmc.finder.api.user.dto.UserActivityResponse;
import com.cmc.finder.domain.debate.entity.Debate;
import com.cmc.finder.domain.debate.service.DebateService;
import com.cmc.finder.domain.model.Email;
import com.cmc.finder.domain.qna.question.entity.Question;
import com.cmc.finder.domain.qna.question.application.QuestionService;
import com.cmc.finder.domain.user.entity.User;
import com.cmc.finder.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserActivityService {

    private final UserService userService;
    private final QuestionService questionService;
    private final DebateService debateService;

    public List<UserActivityResponse> getUserActivity(String email) {

        User user = userService.getUserByEmail(Email.of(email));

        //TODO 커뮤니티 추가

        List<Question> questionList = questionService.getQuestionsByUser(user);
        List<Debate> debateList = debateService.getDebateByUser(user);

        List<UserActivityResponse> userActivityResponses = new ArrayList<>();

        // question to userActivityResponse
        List<UserActivityResponse> collect = questionList.stream().map(question ->
                UserActivityResponse.of(question)
        ).collect(Collectors.toList());

        userActivityResponses.addAll(collect);

        //TODO 삭제

        // debate to userActivityResponse
        List<UserActivityResponse> collect2 = debateList.stream().map(debate ->
                UserActivityResponse.of(debate)
        ).collect(Collectors.toList());

        userActivityResponses.addAll(collect2);

        // 재정렬
        List<UserActivityResponse> sortedResponse = userActivityResponses.stream().sorted(new Comparator<UserActivityResponse>() {
            @Override
            public int compare(UserActivityResponse o1, UserActivityResponse o2) {
                return o2.getCreateTime().compareTo(o1.getCreateTime());
            }
        }).collect(Collectors.toList());

        return sortedResponse;

    }





}
