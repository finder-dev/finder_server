package com.cmc.finder.api.auth.token.dto;

import com.cmc.finder.domain.jwt.constant.GrantType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class AccessTokenResponse {

    private String grantType;

    private String accessToken;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private Date accessTokenExpireTime;

    public static AccessTokenResponse of(String accessToken, Date accessTokenExpireTime) {
        return AccessTokenResponse.builder()
                .grantType(GrantType.BEARRER.getType())
                .accessToken(accessToken)
                .accessTokenExpireTime(accessTokenExpireTime)
                .build();
    }

}
