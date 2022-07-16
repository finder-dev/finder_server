package com.cmc.finder.domain.user.service;

import com.cmc.finder.domain.user.entity.User;
import com.cmc.finder.domain.user.repository.UserRepository;
import com.cmc.finder.domain.user.validator.UserValidator;
import com.cmc.finder.domain.model.Email;
import com.cmc.finder.global.error.exception.EntityNotFoundException;
import com.cmc.finder.global.error.exception.ErrorCode;
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
                .orElseThrow(()-> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(()-> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    public Boolean existsUser(Email email) {
        return userRepository.existsByEmail(email);
    }


    @Transactional
    public User updateUser(Email email, User updateUser) {
        userValidator.validateDuplicateNickname(updateUser.getNickname());

        User savedUser = getUserByEmail(email);
        savedUser.update(updateUser);

        return savedUser;

    }

    @Transactional
    public void deleteUser(Email email) {
        User savedUser = getUserByEmail(email);
        savedUser.quit();
    }
}
