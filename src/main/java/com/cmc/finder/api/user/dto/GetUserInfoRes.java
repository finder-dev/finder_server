package com.cmc.finder.api.user.dto;

import com.cmc.finder.domain.keyword.entity.Keyword;
import com.cmc.finder.domain.user.entity.User;
import com.cmc.finder.domain.model.MBTI;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetUserInfoRes {

    private String email;

    private MBTI mbti;

    private String nickname;

    public static GetUserInfoRes of(User user) {

        return GetUserInfoRes.builder()
                .email(user.getEmail().getValue())
                .mbti(user.getMbti())
                .nickname(user.getNickname())
                .build();

    }
}
