package com.cmc.finder.api.debate.service;

import com.cmc.finder.api.debate.dto.*;
import com.cmc.finder.api.debate.repository.DebateRepositoryCustom;
import com.cmc.finder.domain.debate.constant.DebateState;
import com.cmc.finder.domain.debate.constant.Option;
import com.cmc.finder.domain.debate.entity.Debate;
import com.cmc.finder.domain.debate.entity.DebateAnswer;
import com.cmc.finder.domain.debate.entity.Debater;
import com.cmc.finder.domain.debate.service.DebateAnswerService;
import com.cmc.finder.domain.debate.service.DebateService;
import com.cmc.finder.domain.debate.service.DebaterService;
import com.cmc.finder.domain.model.Email;
import com.cmc.finder.domain.user.entity.User;
import com.cmc.finder.domain.user.service.UserService;
import com.cmc.finder.global.error.exception.AuthenticationException;
import com.cmc.finder.global.error.exception.ErrorCode;
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
    private final DebateRepositoryCustom debateRepositoryCustom;

    public DebateCreateDto.Response createDebate(DebateCreateDto.Request request, String email) {
        User user = userService.getUserByEmail(Email.of(email));

        // 토론생성
        Debate debate = request.toEntity();
        Debate saveDebate = Debate.createDebate(debate, user);

        saveDebate = debateService.saveDebate(saveDebate);

        return DebateCreateDto.Response.of(saveDebate);

    }

    @Transactional
    public DebateJoinDto.Response joinOrDetachDebate(DebateJoinDto.Request request, Long debateId, String email) {

        User user = userService.getUserByEmail(Email.of(email));
        Debate debate = debateService.getDebate(debateId);

        Option option = Option.from(request.getOption());

        // 기존에 토론 참여한 인원 확인
        if (debaterService.existsDebater(user, debate)){

            Debater debater = debaterService.getDebater(user, debate);

            // 토론 선택지 변경
            if (Option.equal(debater.getOption(), option)) {

                debaterService.deleteDebater(debater);
                return DebateJoinDto.Response.of(debater, false);

            }
            // 토론 선택지 취소
            else {
                debater.updateOption(option);
            }

            return DebateJoinDto.Response.of(debater, true);

        // 토론 참여
        }else {
            // 토론자 생성
            Debater saveDebater = Debater.createDebater(debate, user, option);
            saveDebater = debaterService.saveDebater(saveDebater);

            return DebateJoinDto.Response.of(saveDebater, true);
        }


    }

    public Page<DebateSimpleDto.Response> getDebateList(DebateSimpleDto.Request request, Pageable pageable) {

        DebateState debateState = request.getState() != null ? DebateState.from(request.getState()) : DebateState.PROCEEDING;

        Page<DebateSimpleDto.Response> debateSimpleDto = debateRepositoryCustom.findDebateSimpleDto(debateState, pageable);
        return debateSimpleDto;

    }

    public DebateDetailDto getDebateDetail(Long debateId) {

        Debate debate = debateService.getDebate(debateId);
        List<DebateAnswer> debateAnswers = debateAnswerService.getDebateAnswersByDebate(debate);

        // Option A count
        Long countA = debaterService.getDebaterCountByOption(debate, Option.A);

        // Option B count
        Long countB = debaterService.getDebaterCountByOption(debate, Option.B);

        return DebateDetailDto.of(debate, debateAnswers, countA, countB);
    }

    @Transactional
    public DebateAnswerCreateDto.Response createDebateAnswer(Long debateId, DebateAnswerCreateDto.Request request, String email) {

        Debate debate = debateService.getDebate(debateId);
        User user = userService.getUserByEmail(Email.of(email));

        DebateAnswer debateAnswer = request.toEntity();
        DebateAnswer saveDebateAnswer = DebateAnswer.createDebateAnswer(user, debate, debateAnswer);

        saveDebateAnswer = debateAnswerService.saveDebateAnswer(saveDebateAnswer);

        return DebateAnswerCreateDto.Response.of(saveDebateAnswer);

    }

    @Transactional
    public DebateDeleteDto deleteDebate(Long debateId, String email) {

        User user = userService.getUserByEmail(Email.of(email));
        Debate debate = debateService.getDebate(debateId);

        // 유저 검증
        if (debate.getWriter() != user) {
            throw new AuthenticationException(ErrorCode.DEBATE_USER_BE_NOT_WRITER);
        }

        debateService.deleteDebate(debate);

        return DebateDeleteDto.of();

    }

    @Transactional
    public DebateAnswerDeleteDto deleteDebateAnswer(Long debateAnswerId, String email) {

        User user = userService.getUserByEmail(Email.of(email));
        DebateAnswer debateAnswer = debateAnswerService.getDebateAnswer(debateAnswerId);

        // 유저 검증
        if (debateAnswer.getUser() != user) {
            throw new AuthenticationException(ErrorCode.DEBATE_ANSWER_USER_BE_NOT_WRITER);
        }

        debateAnswerService.deleteDebateAnswer(debateAnswer);

        return DebateAnswerDeleteDto.of();

    }
}
