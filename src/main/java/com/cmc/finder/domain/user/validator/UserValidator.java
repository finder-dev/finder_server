package com.cmc.finder.domain.user.validator;

import com.cmc.finder.domain.user.exception.DuplicateKeywordException;
import com.cmc.finder.domain.user.exception.EmailDuplicateException;
import com.cmc.finder.domain.user.exception.NicknameDuplicateException;
import com.cmc.finder.domain.user.repository.UserRepository;
import com.cmc.finder.domain.model.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository userRepository;

    public void validateDuplicateUser(Email email, String nickname) {
        validateDuplicateEmail(email);
        validateDuplicateNickname(nickname);

    }

    public void validateDuplicateEmail(Email email) {
        if (userRepository.existsByEmail(email)) {
            throw new EmailDuplicateException();
        }

    }

    public void validateDuplicateNickname(String nickname) {
        if (userRepository.existsByNickname(nickname)) {
            throw new NicknameDuplicateException();
        }
    }

    public Boolean validateDuplicateKeywords(List<String> keywords) {

        if (keywords.size() != keywords.stream().distinct().count()) {
            throw new DuplicateKeywordException();
        }

        return true;
    }

}
