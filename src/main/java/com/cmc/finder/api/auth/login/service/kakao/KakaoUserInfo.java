package com.cmc.finder.api.auth.login.service.kakao;

import com.cmc.finder.domain.user.constant.UserType;
import com.cmc.finder.domain.user.entity.User;
import com.cmc.finder.domain.model.Email;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter @Setter
@JsonIgnoreProperties({"connected_at"})
@ToString
public class KakaoUserInfo {

    private String id;

    private Map<String, String> properties;

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @Getter @Setter
    @JsonIgnoreProperties({"profile_nickname_needs_agreement", "profile_image_needs_agreement", "has_email",
            "email_needs_agreement", "is_email_valid", "is_email_verified"})
    public static class KakaoAccount {

        private String email;

        private Profile profile;

        @Getter @Setter
        public static class Profile {
            private String nickname;
        }

    }

    public User toEntity() {
        return User.builder()
                .email(Email.of(this.getKakaoAccount().getEmail()))
                .userType(UserType.KAKAO)
                .nickname(this.getProperties().get("nickname"))
                .build();
    }



}