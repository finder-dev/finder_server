package com.cmc.finder.domain.authcode.service;

import com.cmc.finder.domain.authcode.entity.AuthCode;
import com.cmc.finder.domain.authcode.exception.AuthCodeNotEqualException;
import com.cmc.finder.domain.authcode.exception.AuthCodeNotFoundException;
import com.cmc.finder.domain.authcode.repository.AuthCodeRedisRepository;
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
                .orElseThrow(AuthCodeNotFoundException::new);
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
