package com.cmc.finder.api.debate.service;

import com.cmc.finder.api.debate.dto.DebateCreateDto;
import com.cmc.finder.api.debate.dto.DebateSimpleDto;
import com.cmc.finder.api.debate.dto.JoinDebateDto;
import com.cmc.finder.domain.debate.constant.Option;
import com.cmc.finder.domain.debate.entity.Debate;
import com.cmc.finder.domain.debate.entity.Debater;
import com.cmc.finder.domain.debate.service.DebateService;
import com.cmc.finder.domain.debate.service.DebaterService;
import com.cmc.finder.domain.model.Email;
import com.cmc.finder.domain.model.MBTI;
import com.cmc.finder.domain.user.entity.User;
import com.cmc.finder.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ApiDebateService {

    private final DebateService debateService;
    private final DebaterService debaterService;
    private final UserService userService;

    public DebateCreateDto.Response createDebate(DebateCreateDto.Request request, String email) {
        User user = userService.getUserByEmail(Email.of(email));

        // 토론생성
        Debate debate = request.toEntity();
        Debate saveDebate = Debate.createDebate(debate, user);

        saveDebate = debateService.saveDebate(saveDebate);

        return DebateCreateDto.Response.of(saveDebate);

    }

    @Transactional
    public JoinDebateDto.Response joinOrDetachDebate(JoinDebateDto.Request request, Long debateId, String email) {

        User user = userService.getUserByEmail(Email.of(email));
        Debate debate = debateService.getDebate(debateId);

        Option option = Option.from(request.getOption());

        // 기존에 토론 참여한 인원 확인
        if (debaterService.existsDebater(user, debate)){

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
        }else {
            // 토론자 생성
            Debater saveDebater = Debater.createDebater(debate, user, option);
            saveDebater = debaterService.saveDebater(saveDebater);

            return JoinDebateDto.Response.of(saveDebater, true);
        }


    }

//    public Page<DebateSimpleDto.Response> getDebateList(Pageable pageable) {
//    }
}
