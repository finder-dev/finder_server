package com.cmc.finder.domain.jwt.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;
import java.util.Date;

@RedisHash(value = "refreshToken", timeToLive = 900000)
@Getter
@AllArgsConstructor
@Builder
@ToString
public class RefreshToken {

    @Id
    private String id;

    private String refreshToken;

    private Date expiration;

    public static RefreshToken of(String email, String refreshToken, Date expiration) {
        return RefreshToken.builder()
                .id(email)
                .refreshToken(refreshToken)
                .expiration(expiration)
                .build();
    }
}
