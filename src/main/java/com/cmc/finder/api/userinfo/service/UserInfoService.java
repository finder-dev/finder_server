package com.cmc.finder.api.userinfo.service;

import com.cmc.finder.api.auth.signup.dto.NicknameCheckDto;
import com.cmc.finder.api.userinfo.dto.UpdateMBTIDto;
import com.cmc.finder.api.userinfo.dto.UpdateNicknameDto;
import com.cmc.finder.api.userinfo.dto.UpdateProfileImgDto;
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
import org.springframework.web.multipart.MultipartFile;

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
        user.updateProfileUrl(imageName);

        String path = s3Uploader.getUrl(PATH, imageName);
        return UpdateProfileImgDto.Response.of(path);

    }

    @Transactional
    public UpdateNicknameDto.Response updateNickname(UpdateNicknameDto.Request request, String email) {

        User user = userService.getUserByEmail(Email.of(email));
        String nickname = request.getNickname();

        // nickname 중복 확인
        if (userValidator.validateDuplicateNickname(nickname)) {
            throw new NicknameDuplicateException();
        }

        user.updateNickname(nickname);

        return UpdateNicknameDto.Response.of(nickname);

    }

    @Transactional
    public UpdateMBTIDto.Response updateMBTI(UpdateMBTIDto.Request request, String email) {
        User user = userService.getUserByEmail(Email.of(email));

        MBTI mbti = MBTI.from(request.getMbti());
        user.updateMBTI(mbti);

        return UpdateMBTIDto.Response.of(mbti);

    }
}
