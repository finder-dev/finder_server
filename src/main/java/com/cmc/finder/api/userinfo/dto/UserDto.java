package com.cmc.finder.api.userinfo.dto;

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
@ToString
public class UserDto {

    private String email;

    private MBTI mbti;

    private String nickname;

    private String profileUrl;

    private List<String> keywords;

    public static UserDto of(User user, List<Keyword> keywordByMember) {

        List<String> keywords = keywordByMember.stream().map(keyword -> keyword.getKeyword()).collect(Collectors.toList());

        return UserDto.builder()
                .email(user.getEmail().getValue())
                .mbti(user.getMbti())
                .nickname(user.getNickname())
                .profileUrl(user.getProfileImg())
                .keywords(keywords)
                .build();

    }
}
