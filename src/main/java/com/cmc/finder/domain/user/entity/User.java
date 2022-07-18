package com.cmc.finder.domain.user.entity;

import com.cmc.finder.domain.base.BaseTimeEntity;
import com.cmc.finder.domain.user.constant.UserType;
import com.cmc.finder.domain.model.Email;
import com.cmc.finder.domain.model.MBTI;
import com.cmc.finder.domain.model.Password;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE member SET is_delete = true WHERE id=?")
@Where(clause = "is_deleted=false")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private UserType userType;

    @Embedded
    private Email email;

    @Embedded
    private Password password;

    @Column(length = 6, unique = true)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private MBTI mbti;

    @Column(length = 200)
    private String profileImg;

//    @Column(nullable = false)
    private String fcmToken;

    private Boolean isActive;

    private Boolean isDeleted;

    @Builder
    public User(UserType userType, Email email, Password password,
                String nickname, MBTI mbti,String fcmToken, String profileImg) {

        this.userType = userType;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.mbti = mbti;
        this.profileImg = profileImg;
        this.fcmToken = fcmToken;
        this.isActive = true;
        this.isDeleted = false;

    }

    public static User createUser(User user) {


        return User.builder()
                .userType(user.getUserType())
                .email(user.getEmail())
                .password(user.getPassword())
                .nickname(user.getNickname())
                .mbti(user.getMbti())
                .fcmToken(user.getFcmToken())
                .profileImg(user.getProfileImg())
                .build();
    }

    public void updateProfileImage(String profileImage) {
        this.profileImg = profileImage;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateMBTI(MBTI mbti) {
        this.mbti = mbti;
    }

    public void updateFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public void update(User updateUser) {

        updateMBTI(updateUser.getMbti());
        updateNickname(updateUser.getNickname());
        if (updateUser.getPassword() != null) {
            this.password = updateUser.getPassword();
        }

    }

    public void quit() {
        this.nickname = UUID.randomUUID().toString();
        this.email = Email.getRandomValue();
        this.isDeleted = true;
    }
}