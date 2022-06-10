package com.cmc.finder.domain.jwt.service;

import com.cmc.finder.domain.jwt.domain.RefreshToken;
import com.cmc.finder.domain.jwt.exception.RefreshTokenNotFountException;
import com.cmc.finder.domain.jwt.repository.RefreshTokenRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class RefreshTokenRedisService {

    private final RefreshTokenRedisRepository refreshTokenRedisRepository;

    public void saveRefreshToken(RefreshToken refreshToken){
        refreshTokenRedisRepository.save(refreshToken);
    }

    public RefreshToken getRefreshTokenByEmail(String email){
        RefreshToken refreshToken = refreshTokenRedisRepository.findById(email)
                .orElseThrow(RefreshTokenNotFountException::new);
        return refreshToken;
    }

    public void deleteRefreshToken(String email) {
        refreshTokenRedisRepository.deleteById(email);
    }
}
