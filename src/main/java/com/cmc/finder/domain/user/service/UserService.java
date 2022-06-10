package com.cmc.finder.domain.user.service;

import com.cmc.finder.domain.user.entity.User;
import com.cmc.finder.domain.user.exception.UserNotFoundException;
import com.cmc.finder.domain.user.repository.UserRepository;
import com.cmc.finder.domain.user.validator.UserValidator;
import com.cmc.finder.domain.model.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final UserValidator userValidator;

    public void register(User user) {

        validateRegisterMember(user);
        userRepository.save(user);

    }

    private void validateRegisterMember(User user) {
        userValidator.validateDuplicateUser(user.getEmail(), user.getNickname());
    }

    public User getUserByEmail(Email email) {
        return userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
    }

    public Boolean existsUser(Email email) {
        return userRepository.existsByEmail(email);
    }

//    public Member findMemberByRefreshToken(String refreshToken) {
//
//        return memberRepository.findByRefreshToken(refreshToken)
//                .orElseThrow(() -> new TokenNotFoundException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));
//
//    }
}
