package com.cmc.finder.domain.jwt.domain;

import com.cmc.finder.domain.model.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@RedisHash("refreshToken")
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
