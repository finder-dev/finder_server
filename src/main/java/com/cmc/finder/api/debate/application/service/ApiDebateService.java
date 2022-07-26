package com.cmc.finder.api.debate.application.service;

import com.cmc.finder.api.debate.dto.*;
import com.cmc.finder.domain.block.application.BlockService;
import com.cmc.finder.domain.debate.constant.DebateState;
import com.cmc.finder.domain.debate.constant.Option;
import com.cmc.finder.domain.debate.entity.Debate;
import com.cmc.finder.domain.debate.entity.DebateAnswer;
import com.cmc.finder.domain.debate.entity.Debater;
import com.cmc.finder.domain.debate.application.DebateAnswerService;
import com.cmc.finder.domain.debate.application.DebateService;
import com.cmc.finder.domain.debate.application.DebaterService;
import com.cmc.finder.domain.debate.exception.ClosedDebateException;
import com.cmc.finder.domain.debate.exception.SameOptionsException;
import com.cmc.finder.domain.model.Email;
import com.cmc.finder.domain.model.ServiceType;
import com.cmc.finder.domain.report.application.ReportService;
import com.cmc.finder.domain.report.entity.Report;
import com.cmc.finder.domain.report.exception.AlreadyReceivedReportException;
import com.cmc.finder.domain.user.entity.User;
import com.cmc.finder.domain.user.service.UserService;
import com.cmc.finder.global.error.exception.ErrorCode;
import com.cmc.finder.infra.notification.FcmService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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
    private final ReportService reportService;
    private final BlockService blockService;


    @Transactional
    public CreateDebateDto.Response createDebate(CreateDebateDto.Request request, String email) {
        User user = userService.getUserByEmail(Email.of(email));

        if (request.getOptionA().equals(request.getOptionB())) {
            throw new SameOptionsException(ErrorCode.SAME_OPTIONS);
        }

        // 토론생성
        Debate debate = request.toEntity();
        Debate saveDebate = Debate.createDebate(debate, user);

        saveDebate = debateService.saveDebate(saveDebate);

        return CreateDebateDto.Response.from(saveDebate);

    }

    @Transactional
    public JoinDebateDto.Response joinOrDetachDebate(JoinDebateDto.Request request, Long debateId, String email) {

        User user = userService.getUserByEmail(Email.of(email));
        Debate debate = debateService.getDebate(debateId);

        Option option = Option.from(request.getOption());

        // 마감된 토론 처리
        if (debate.getState() == DebateState.COMPLETE) {
            throw new ClosedDebateException(ErrorCode.CLOSED_DEBATE);
        }

        // 기존에 토론 참여한 인원 확인
        if (debaterService.existsDebater(user, debate)) {

            Debater debater = debaterService.getDebater(user, debate);

            // 토론 선택지 변경
            if (Option.equal(debater.getOption(), option)) {

                debaterService.deleteDebater(debater);
                return JoinDebateDto.Response.from(debater, false);

            }
            // 토론 선택지 취소
            else {
                debater.updateOption(option);
            }

            return JoinDebateDto.Response.from(debater, true);

            // 토론 참여
        } else {
            // 토론자 생성
            Debater saveDebater = Debater.createDebater(debate, user, option);
            saveDebater = debaterService.saveDebater(saveDebater);

            return JoinDebateDto.Response.from(saveDebater, true);
        }


    }

    public Slice<DebateSimpleDto.Response> getDebateList(DebateSimpleDto.Request request, String email, Pageable pageable) {

        User user = userService.getUserByEmail(Email.of(email));

        DebateState debateState = request.getState() != null ? DebateState.from(request.getState()) : DebateState.PROCEEDING;
        return debateService.getDebateList(debateState, user,  pageable);

    }

    public DebateDetailDto getDebateDetail(Long debateId, String email) {

        Debate debate = debateService.getDebate(debateId);
        User user = userService.getUserByEmail(Email.of(email));
        Debater debater = null;
        Boolean join = debaterService.existsDebater(user, debate);

        if (join) {
            debater = debaterService.getDebater(user, debate);
        }

        // 신고 조회
        List<Long> reportedServiceId = reportService.getReportsByUser(user, ServiceType.COMMUNITY_ANSWER);

        // 차단 조회
        List<User> blockedUser = blockService.getBlockUser(user);


        // 답변 조회 -> id 역순
        List<DebateAnswer> debateAnswers = debateAnswerService.getDebateAnswersByDebate(debate);

        // Option A count
        Long countA = debaterService.getDebaterCountByOption(debate, Option.A);

        // Option B count
        Long countB = debaterService.getDebaterCountByOption(debate, Option.B);

        return DebateDetailDto.of(debate, debateAnswers, join, countA, countB, debater, reportedServiceId, blockedUser);
    }


    public GetHotDebateRes getHotDebate(String email) {

        User user = userService.getUserByEmail(Email.of(email));

        Debate debate = debateService.getHotDebate(user);

        Boolean join = debaterService.existsDebater(user, debate);
        Debater debater = null;

        if (join) {
            debater = debaterService.getDebater(user, debate);
        }

        // Option A count
        Long countA = debaterService.getDebaterCountByOption(debate, Option.A);

        // Option B count
        Long countB = debaterService.getDebaterCountByOption(debate, Option.B);

        return GetHotDebateRes.of(debate, countA, countB, join, debater);

    }


    @Transactional
    public ReportDebateRes reportDebate(Long debateId, String email) {

        Debate debate = debateService.getDebate(debateId);
        User from = userService.getUserByEmail(Email.of(email));

        Report report = Report.createReport(ServiceType.DEBATE, from, debate.getWriter(), debateId);

        if (reportService.alreadyReceivedReport(report)) {
            throw new AlreadyReceivedReportException(ErrorCode.ALREADY_RECEIVED_REPORT);
        }

        reportService.create(report);

        return ReportDebateRes.of();

    }
}
