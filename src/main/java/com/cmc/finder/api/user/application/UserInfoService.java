package com.cmc.finder.api.user.application;

import com.cmc.finder.api.user.dto.*;
import com.cmc.finder.domain.model.Email;
import com.cmc.finder.domain.model.MBTI;
import com.cmc.finder.domain.model.Password;
import com.cmc.finder.domain.user.entity.User;
import com.cmc.finder.domain.user.application.UserService;
import com.cmc.finder.domain.user.validator.UserValidator;
import com.cmc.finder.infra.file.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserInfoService {

    @Value("${s3.users.path}")
    private String PATH;

    @Value("${s3.users.default}")
    private String DEFAULT_IMG;

    private final UserService userService;
    private final UserValidator userValidator;
    private final S3Uploader s3Uploader;

    @Transactional
    public UpdateProfileImgDto.Response updateProfileImg(UpdateProfileImgDto.Request request, String email) {

        User user = userService.getUserByEmail(Email.of(email));

        // 기존 이미지 삭제
        if (!DEFAULT_IMG.equals(user.getProfileImg())) {

            String profileImg = user.getProfileImg();
            s3Uploader.deleteFile(profileImg, PATH);
        }

        // 새로운 이미지 등록
        String imageName = s3Uploader.uploadFile(request.getProfileImg(), PATH);
        user.updateProfileImage(imageName);

        String path = s3Uploader.getUrl(PATH, imageName);
        return UpdateProfileImgDto.Response.from(path);

    }

    @Transactional
    public UpdateNicknameDto.Response updateNickname(UpdateNicknameDto.Request request, String email) {

        User user = userService.getUserByEmail(Email.of(email));
        String nickname = request.getNickname();

        // nickname 중복 확인
        userValidator.validateDuplicateNickname(nickname);

        user.updateNickname(nickname);

        return UpdateNicknameDto.Response.from(nickname);

    }

    @Transactional
    public UpdateMBTIDto.Response updateMBTI(UpdateMBTIDto.Request request, String email) {
        User user = userService.getUserByEmail(Email.of(email));

        MBTI mbti = MBTI.from(request.getMbti());
        user.updateMBTI(mbti);

        return UpdateMBTIDto.Response.from(mbti);

    }

    public GetUserInfoRes getUserInfo(String email) {

        User user = userService.getUserByEmail(Email.of(email));
        return GetUserInfoRes.from(user);

    }

    @Transactional
    public UpdateUserDto.Response updateUser(UpdateUserDto.Request request, String email) {

        Password password = null;
        if (request.getPassword() != null) {
            password = Password.builder().value(request.getPassword()).build();
        }
        User updateUser = request.toEntity(password);
        User updatedUser = userService.updateUser(Email.of(email), updateUser);

        return UpdateUserDto.Response.from(updatedUser);

    }

    @Transactional
    public DeleteUserRes deleteUser(String email) {
        userService.deleteUser(Email.of(email));
        return DeleteUserRes.of();
    }

    @Transactional
    public NotificationOnOffRes onOffNotificaiton(String email) {
        User user = userService.getUserByEmail(Email.of(email));
        user.updateNotification();

        return NotificationOnOffRes.from(user.getIsActive());
    }

    public GetNotificationActiveRes getNotificaitonActive(String email) {
        User user = userService.getUserByEmail(Email.of(email));

        return GetNotificationActiveRes.from(user);
    }

}
