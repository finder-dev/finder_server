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

    @Column(length = 20)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private MBTI mbti;

    @Column(length = 200, nullable = false)
    private String profileImg;

    private boolean isDeleted;

    @Builder
    public User(UserType userType, Email email, Password password,
                String nickname, MBTI mbti, String profileImg) {

        this.userType = userType;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.mbti = mbti;
        this.profileImg = profileImg;
        this.isDeleted = false;

    }

    public static User createMember(User user) {

        return User.builder()
                .userType(user.getUserType())
                .email(user.getEmail())
                .password(user.getPassword())
                .nickname(user.getNickname())
                .mbti(user.mbti)
                .profileImg(user.profileImg)
                .build();
    }

    public void updateProfileUrl(String profileUrl) {
        this.profileImg = profileUrl;
    }


}