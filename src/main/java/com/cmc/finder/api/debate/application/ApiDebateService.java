package com.cmc.finder.api.debate.application;

import com.cmc.finder.api.debate.dto.*;
import com.cmc.finder.domain.debate.constant.DebateState;
import com.cmc.finder.domain.debate.constant.Option;
import com.cmc.finder.domain.debate.entity.Debate;
import com.cmc.finder.domain.debate.entity.DebateAnswer;
import com.cmc.finder.domain.debate.entity.Debater;
import com.cmc.finder.domain.debate.application.DebateAnswerService;
import com.cmc.finder.domain.debate.application.DebateService;
import com.cmc.finder.domain.debate.application.DebaterService;
import com.cmc.finder.domain.model.Email;
import com.cmc.finder.domain.model.Type;
import com.cmc.finder.domain.notification.entity.Notification;
import com.cmc.finder.domain.notification.application.NotificationService;
import com.cmc.finder.domain.user.entity.User;
import com.cmc.finder.domain.user.service.UserService;
import com.cmc.finder.infra.notification.FcmService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ApiDebateService {

    private final DebateService debateService;
    private final DebaterService debaterService;
    private final DebateAnswerService debateAnswerService;
    private final UserService userService;
    private final FcmService fcmService;
    private final NotificationService notificationService;


    public CreateDebateDto.Response createDebate(CreateDebateDto.Request request, String email) {
        User user = userService.getUserByEmail(Email.of(email));

        // 토론생성
        Debate debate = request.toEntity();
        Debate saveDebate = Debate.createDebate(debate, user);

        saveDebate = debateService.saveDebate(saveDebate);

        return CreateDebateDto.Response.of(saveDebate);

    }

    @Transactional
    public JoinDebateDto.Response joinOrDetachDebate(JoinDebateDto.Request request, Long debateId, String email) {

        User user = userService.getUserByEmail(Email.of(email));
        Debate debate = debateService.getDebate(debateId);

        Option option = Option.from(request.getOption());

        // 기존에 토론 참여한 인원 확인
        if (debaterService.existsDebater(user, debate)) {

            Debater debater = debaterService.getDebater(user, debate);

            // 토론 선택지 변경
            if (Option.equal(debater.getOption(), option)) {

                debaterService.deleteDebater(debater);
                return JoinDebateDto.Response.of(debater, false);

            }
            // 토론 선택지 취소
            else {
                debater.updateOption(option);
            }

            return JoinDebateDto.Response.of(debater, true);

            // 토론 참여
        } else {
            // 토론자 생성
            Debater saveDebater = Debater.createDebater(debate, user, option);
            saveDebater = debaterService.saveDebater(saveDebater);

            return JoinDebateDto.Response.of(saveDebater, true);
        }


    }

    public Page<DebateSimpleDto.Response> getDebateList(DebateSimpleDto.Request request, Pageable pageable) {

        DebateState debateState = request.getState() != null ? DebateState.from(request.getState()) : DebateState.PROCEEDING;
        return debateService.getDebateList(debateState, pageable);

    }

    public DebateDetailDto getDebateDetail(Long debateId) {

        Debate debate = debateService.getDebate(debateId);

        // 답변 조회 -> id 역순
        List<DebateAnswer> debateAnswers = debateAnswerService.getDebateAnswersByDebate(debate);

        // Option A count
        Long countA = debaterService.getDebaterCountByOption(debate, Option.A);

        // Option B count
        Long countB = debaterService.getDebaterCountByOption(debate, Option.B);

        return DebateDetailDto.of(debate, debateAnswers, countA, countB);
    }


    public GetHotDebateRes getHotDebate(String email) {

        Debate debate = debateService.getHotDebate();
        User user = userService.getUserByEmail(Email.of(email));

        Boolean join = debaterService.existsDebater(user, debate);

        // Option A count
        Long countA = debaterService.getDebaterCountByOption(debate, Option.A);

        // Option B count
        Long countB = debaterService.getDebaterCountByOption(debate, Option.B);

        return GetHotDebateRes.of(debate, countA, countB, join);
    }


    private void createNotification(Debate debate, String content) {
        Notification notification = Notification.createNotification(debate.getTitle(), content, Type.DEBATE, debate.getWriter(), debate.getDebateId());
        notificationService.create(notification);
    }

}
