package com.cmc.finder.domain.authcode.application;

import com.cmc.finder.domain.authcode.entity.AuthCode;
import com.cmc.finder.domain.authcode.exception.AuthCodeNotEqualException;
import com.cmc.finder.domain.authcode.repository.AuthCodeRedisRepository;
import com.cmc.finder.global.error.exception.EntityNotFoundException;
import com.cmc.finder.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthCodeService {

    private final AuthCodeRedisRepository authCodeRedisRepository;

    public void saveAuthCode(AuthCode code){
        authCodeRedisRepository.save(code);
    }

    public AuthCode getAuthCodeByEmail(String email){
        AuthCode authCode = authCodeRedisRepository.findById(email)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.AUTH_CODE_NOT_FOUND));
        return authCode;
    }

    public void deleteAuthCode(String email) {
        authCodeRedisRepository.deleteById(email);
    }

    public void authenticateCode(String email, String code) {
        AuthCode authCode = getAuthCodeByEmail(email);

        if (!code.equals(authCode.getCode())) {
            throw new AuthCodeNotEqualException();
        }

        deleteAuthCode(email);

    }

}
