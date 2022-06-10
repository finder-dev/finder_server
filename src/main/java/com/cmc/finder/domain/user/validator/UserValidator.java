package com.cmc.finder.domain.user.validator;

import com.cmc.finder.domain.keyword.exception.DuplicateKeywordException;
import com.cmc.finder.domain.user.exception.EmailDuplicateException;
import com.cmc.finder.domain.user.exception.NicknameDuplicateException;
import com.cmc.finder.domain.user.repository.UserRepository;
import com.cmc.finder.domain.model.Email;
import com.cmc.finder.global.error.exception.AuthenticationException;
import com.cmc.finder.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository userRepository;

    public void validateDuplicateUser(Email email, String nickname) {

        if (validateDuplicateEmail(email)) {
            throw new EmailDuplicateException();
        }

        if (validateDuplicateNickname(nickname)) {
            throw new NicknameDuplicateException();
        }

    }

    public Boolean validateDuplicateEmail(Email email) {

        return userRepository.existsByEmail(email);

    }

    public Boolean validateDuplicateNickname(String nickname) {

        return userRepository.existsByNickname(nickname);

    }

    public Boolean validateDuplicateKeywords(List<String> keywords) {

        if (keywords.size() != keywords.stream().distinct().count()) {
            throw new DuplicateKeywordException();
        }

        return true;
    }

//    public void validateRefreshTokenExpirationTime(LocalDateTime refreshTokenExpirationTime, LocalDateTime now) {
//
//        if(refreshTokenExpirationTime.isBefore(now)) {
//
//            throw new AuthenticationException(ErrorCode.REFRESH_TOKEN_EXPIRED);
//        }
//
//    }
}
