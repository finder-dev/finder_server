package com.cmc.finder.api.userinfo.service;

import com.cmc.finder.api.userinfo.dto.UpdateProfileDto;
import com.cmc.finder.domain.model.Email;
import com.cmc.finder.domain.user.entity.User;
import com.cmc.finder.domain.user.service.UserService;
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
    private final S3Uploader s3Uploader;

    @Transactional
    public UpdateProfileDto updateProfileImg(MultipartFile profile, String email) {

        User user = userService.getUserByEmail(Email.of(email));

        // 기존 이미지 삭제
        if (!DEFAULT_IMG.equals(user.getProfileImg())) {

            String profileImg = user.getProfileImg();
            s3Uploader.deleteFile(profileImg, PATH);
        }

        // 새로운 이미지 등록
        String url = s3Uploader.uploadFile(profile, PATH);
        user.updateProfileUrl(url);

        String path = s3Uploader.getUrl(url, PATH);
        return UpdateProfileDto.of(path);

    }
}
