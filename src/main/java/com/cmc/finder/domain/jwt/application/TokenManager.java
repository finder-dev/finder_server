package com.cmc.finder.domain.jwt.application;

import com.cmc.finder.domain.jwt.constant.GrantType;
import com.cmc.finder.domain.jwt.constant.TokenType;
import com.cmc.finder.domain.jwt.dto.TokenDto;
import com.cmc.finder.domain.user.exception.NotValidTokenException;
import com.cmc.finder.global.error.exception.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class TokenManager {

    @Value("${token.access-token-expiration-time}")
    private String accessTokenExpirationTime;

    @Value("${token.refresh-token-expiration-time}")
    private String refreshTokenExpirationTime;

    @Value("${token.secret}")
    private String tokenSecret;

    public TokenDto createTokenDto(String email) {
        Date accessTokenExpireTime = createAccessTokenExpireTime();
        Date refreshTokenExpireTime = createRefreshTokenExpireTime();

        String accessToken = createAccessToken(email, accessTokenExpireTime);
        String refreshToken = createRefreshToken(email, refreshTokenExpireTime);
        return TokenDto.builder()
                .grantType(GrantType.BEARRER.getType())
                .accessToken(accessToken)
                .accessTokenExpireTime(accessTokenExpireTime)
                .refreshToken(refreshToken)
                .refreshTokenExpireTime(refreshTokenExpireTime)
                .build();
    }

    public Date createAccessTokenExpireTime() {
        return new Date(System.currentTimeMillis() + Long.parseLong(accessTokenExpirationTime));
    }

    public Date createRefreshTokenExpireTime() {
        return new Date(System.currentTimeMillis() + Long.parseLong(refreshTokenExpirationTime));
    }

    public String createAccessToken(String email, Date expirationTime) {
        String accessToken = Jwts.builder()
                .setSubject(TokenType.ACCESS.name())
                .setAudience(email)
                .setIssuedAt(new Date())
                .setExpiration(expirationTime)
//                .claim("role", role)
                .signWith(SignatureAlgorithm.HS512, tokenSecret)
                .setHeaderParam("typ", "JWT")
                .compact();
        return accessToken;
    }

    public String createRefreshToken(String email, Date expirationTime) {
        String refreshToken = Jwts.builder()
                .setSubject(TokenType.REFRESH.name())
                .setAudience(email)
                .setIssuedAt(new Date())
                .setExpiration(expirationTime)
                .signWith(SignatureAlgorithm.HS512, tokenSecret)
                .setHeaderParam("typ", "JWT")
                .compact();
        return refreshToken;
    }

    public String getUserEmail(String accessToken) {
        String email;
        try {
            Claims claims = Jwts.parser().setSigningKey(tokenSecret)
                    .parseClaimsJws(accessToken).getBody();
            email = claims.getAudience();
        } catch (Exception e){
            e.printStackTrace();
            throw new NotValidTokenException(ErrorCode.NOT_VALID_TOKEN);
        }
        return email;
    }

    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(tokenSecret)
                    .parseClaimsJws(token);
            return true;
        } catch(JwtException e) {  //토큰 변조
            log.info("잘못된 jwt token", e);
        } catch (Exception e){
            log.info("jwt token 검증 중 에러 발생", e);
        }
        return false;
    }

    public Claims getTokenClaims(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(tokenSecret)
                    .parseClaimsJws(token).getBody()
            ;
        } catch (Exception e) {
            e.printStackTrace();
            throw new NotValidTokenException(ErrorCode.NOT_VALID_TOKEN);
        }
        return claims;
    }

    public String getTokenType(String token) {
        String tokenType;
        try {
            Claims claims = Jwts.parser().setSigningKey(tokenSecret)
                    .parseClaimsJws(token).getBody();
            tokenType = claims.getSubject();
        }catch (Exception e) {
            e.printStackTrace();
            throw new NotValidTokenException(ErrorCode.NOT_VALID_TOKEN);
        }

        return tokenType;
    }

    public boolean isTokenExpired(Date tokenExpiredTime) {
        Date now = new Date();
        if(now.after(tokenExpiredTime)) {
            return true;
        }
        return false;
    }

    public String getRole(String token) {

        String role;
        try {
            Claims claims = Jwts.parser().setSigningKey(tokenSecret)
                    .parseClaimsJws(token).getBody();

            role = (String) claims.get("role");
        }catch (Exception e) {
            e.printStackTrace();
            throw new NotValidTokenException(ErrorCode.NOT_VALID_TOKEN);
        }

        return role;
    }
}
