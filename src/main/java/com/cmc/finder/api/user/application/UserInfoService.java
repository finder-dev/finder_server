package com.cmc.finder.api.user.application;

import com.cmc.finder.api.user.dto.MBTIUpdateDto;
import com.cmc.finder.api.user.dto.NicknameUpdateDto;
import com.cmc.finder.api.user.dto.ProfileImgUpdateDto;
import com.cmc.finder.domain.model.Email;
import com.cmc.finder.domain.model.MBTI;
import com.cmc.finder.domain.user.entity.User;
import com.cmc.finder.domain.user.exception.NicknameDuplicateException;
import com.cmc.finder.domain.user.service.UserService;
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
    public ProfileImgUpdateDto.Response updateProfileImg(ProfileImgUpdateDto.Request request, String email) {

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
        return ProfileImgUpdateDto.Response.of(path);

    }

    @Transactional
    public NicknameUpdateDto.Response updateNickname(NicknameUpdateDto.Request request, String email) {

        User user = userService.getUserByEmail(Email.of(email));
        String nickname = request.getNickname();

        // nickname 중복 확인
        if (userValidator.validateDuplicateNickname(nickname)) {
            throw new NicknameDuplicateException();
        }

        user.updateNickname(nickname);

        return NicknameUpdateDto.Response.of(nickname);

    }

    @Transactional
    public MBTIUpdateDto.Response updateMBTI(MBTIUpdateDto.Request request, String email) {
        User user = userService.getUserByEmail(Email.of(email));

        MBTI mbti = MBTI.from(request.getMbti());
        user.updateMBTI(mbti);

        return MBTIUpdateDto.Response.of(mbti);

    }
}
