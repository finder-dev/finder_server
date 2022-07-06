package com.cmc.finder.domain.jwt.application;

import com.cmc.finder.domain.jwt.entity.RefreshToken;
import com.cmc.finder.domain.jwt.repository.RefreshTokenRedisRepository;
import com.cmc.finder.global.error.exception.EntityNotFoundException;
import com.cmc.finder.global.error.exception.ErrorCode;
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
                .orElseThrow(()-> new EntityNotFoundException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));
        return refreshToken;
    }

    public void deleteRefreshToken(String email) {
        refreshTokenRedisRepository.deleteById(email);
    }
}
