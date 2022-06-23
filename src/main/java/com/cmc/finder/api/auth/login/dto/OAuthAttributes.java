package com.cmc.finder.api.auth.login.dto;

import com.cmc.finder.domain.model.MBTI;
import com.cmc.finder.domain.user.constant.UserType;
import com.cmc.finder.domain.user.entity.User;
import com.cmc.finder.domain.model.Email;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.StringUtils;

@Getter
@ToString
@Builder
public class OAuthAttributes {

    private String email;
    private UserType userType;

    public User toUserEntity(OauthLoginDto.Request request, String fileName) {

        User user = User.builder()
                .email(Email.of(email))
                .userType(userType)
                .nickname(request.getNickname())
                .mbti(MBTI.from(request.getMbti()))
                .profileImg("default.png")
                .build();

        if (StringUtils.hasText(fileName)) {
            user.updateProfileImage(fileName);
        }

        return user;
    }

}

