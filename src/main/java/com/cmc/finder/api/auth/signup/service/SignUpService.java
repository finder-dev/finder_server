package com.cmc.finder.api.auth.signup.service;

import com.cmc.finder.api.auth.signup.dto.EmailAuthDto;
import com.cmc.finder.api.auth.signup.dto.EmailSendDto;
import com.cmc.finder.api.auth.signup.dto.NicknameCheckDto;
import com.cmc.finder.api.auth.signup.dto.SignUpDto;
import com.cmc.finder.domain.authcode.entity.AuthCode;
import com.cmc.finder.domain.authcode.application.AuthCodeService;
import com.cmc.finder.domain.jwt.dto.TokenDto;
import com.cmc.finder.domain.jwt.application.TokenManager;
import com.cmc.finder.domain.user.entity.User;
import com.cmc.finder.domain.user.application.UserService;
import com.cmc.finder.domain.user.validator.UserValidator;
import com.cmc.finder.domain.model.Email;
import com.cmc.finder.infra.email.EmailServiceImpl;
import com.cmc.finder.infra.file.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SignUpService {

    @Value("${s3.users.path}")
    private String PATH;

    private final TokenManager tokenManager;
    private final UserService userService;
    private final UserValidator userValidator;
    private final S3Uploader s3Uploader;
    private final EmailServiceImpl emailService;
    private final AuthCodeService authCodeService;

    @Transactional
    public SignUpDto.Response signUpUser(SignUpDto.Request signUpDto) {

        String fileName = "";
        if (signUpDto.getProfileImg() != null) {
            fileName = s3Uploader.uploadFile(signUpDto.getProfileImg(), PATH);
        }

        // User 생성
        User user = signUpDto.toEntity(fileName);
        userService.register(user);

        //JWT 생성
        TokenDto tokenDto = tokenManager.createTokenDto(user.getEmail().getValue());
        return SignUpDto.Response.from(tokenDto);

    }


    public NicknameCheckDto.Response checkNickname(NicknameCheckDto.Request request) {
        userValidator.validateDuplicateNickname(request.getNickname());

        return NicknameCheckDto.Response.of();

    }

    public EmailSendDto.Response sendEmail(String email) {
        userValidator.validateDuplicateEmail(Email.of(email));

        String code = emailService.sendSimpleMessage(email);
        authCodeService.saveAuthCode(AuthCode.of(email, code));

        return EmailSendDto.Response.of();

    }

    public EmailAuthDto.Response checkCode(String email, String code) {
        authCodeService.authenticateCode(email, code);

        return EmailAuthDto.Response.of();
    }
}
