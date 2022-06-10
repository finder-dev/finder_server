package com.cmc.finder.api.auth.signup.service;

import com.cmc.finder.api.auth.signup.dto.EmailCheckDto;
import com.cmc.finder.api.auth.signup.dto.NicknameCheckDto;
import com.cmc.finder.api.auth.signup.dto.SignUpDto;
import com.cmc.finder.domain.jwt.dto.TokenDto;
import com.cmc.finder.domain.jwt.service.TokenManager;
import com.cmc.finder.domain.keyword.entity.Keyword;
import com.cmc.finder.domain.keyword.service.KeywordService;
import com.cmc.finder.domain.user.entity.User;
import com.cmc.finder.domain.user.exception.EmailDuplicateException;
import com.cmc.finder.domain.user.exception.NicknameDuplicateException;
import com.cmc.finder.domain.user.service.UserService;
import com.cmc.finder.domain.user.validator.UserValidator;
import com.cmc.finder.domain.model.Email;
import com.cmc.finder.infra.file.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SignUpService {

    @Value("${s3.users.path}")
    private String PATH;

    private final TokenManager tokenManager;
    private final UserService userService;
    private final UserValidator userValidator;
    private final KeywordService keywordService;
    private final S3Uploader s3Uploader;

    @Transactional
    public SignUpDto.Response signUpUser(SignUpDto.Request signUpDto, MultipartFile profileImg) {

        // 키워드 중복값 검사
        if(signUpDto.getKeywords() != null) {
            userValidator.validateDuplicateKeywords(signUpDto.getKeywords());
        }

        // TODO 타입검사 -> GENERAL
        String fileName = "";
        if (profileImg != null) {
            fileName = s3Uploader.uploadFile(profileImg, PATH);
        }

        // Member 생성
        User user = signUpDto.toEntity(fileName);
        userService.register(user);

        // setKeyword
        if (signUpDto.getKeywords() != null) {
            for (String key : signUpDto.getKeywords()) {
                Keyword keyword = Keyword.createKeyword(key, user);
                keywordService.save(keyword);
            }
        }

        //JWT 생성
        TokenDto tokenDto = tokenManager.createTokenDto(user.getEmail().getValue());
        return SignUpDto.Response.of(tokenDto);

    }

    public EmailCheckDto emailCheck(String email) {

        if (userValidator.validateDuplicateEmail(Email.of(email))) {
            throw new EmailDuplicateException();
        }

        return EmailCheckDto.of();
    }

    public NicknameCheckDto nicknameCheck(String nickname) {

        if (userValidator.validateDuplicateNickname(nickname)) {
            throw new NicknameDuplicateException();
        }

        return NicknameCheckDto.of();

    }
}
