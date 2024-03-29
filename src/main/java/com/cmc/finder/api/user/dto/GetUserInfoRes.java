package com.cmc.finder.api.user.dto;

import com.cmc.finder.domain.user.entity.User;
import com.cmc.finder.domain.model.MBTI;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetUserInfoRes {

    private Long userId;

    private String email;

    private MBTI mbti;

    private String nickname;

    public static GetUserInfoRes from(User user) {

        return GetUserInfoRes.builder()
                .userId(user.getId())
                .email(user.getEmail().getValue())
                .mbti(user.getMbti())
                .nickname(user.getNickname())
                .build();

    }
}
