package com.cmc.finder.api.auth.login.service.apple;

import com.cmc.finder.api.auth.login.service.SocialLoginApiService;
import com.cmc.finder.api.auth.login.dto.OAuthAttributes;
import com.cmc.finder.domain.user.constant.UserType;
import com.cmc.finder.global.error.exception.BusinessException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AppleLoginApiServiceImpl implements SocialLoginApiService {

    private final AppleFeignClient appleFeignClient;

    @Override
    public OAuthAttributes getUserInfo(String identityToken) {

        ApplePublicKeyResponse appleAuthPublicKey = appleFeignClient.getAppleAuthPublicKey();

        String headerOfIdentityToken = identityToken.substring(0, identityToken.indexOf("."));

        Map<String, String> header = null;
        try {
            header = new ObjectMapper().readValue(new String(Base64.getDecoder().decode(headerOfIdentityToken), "UTF-8"), Map.class);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        ApplePublicKeyResponse.Key key = appleAuthPublicKey.getMatchedKeyBy(header.get("kid"), header.get("alg"))
                .orElseThrow(() -> new NullPointerException("Failed get public key from apple's id server."));

        PublicKey publicKey = getPublicKey(key);

        Claims userInfo = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(identityToken).getBody();

        String userId = userInfo.get("sub").toString();

        return OAuthAttributes.builder()
                .email(StringUtils.hasText(userInfo.get("email").toString()) ? userInfo.get("email").toString() : userId)
                .userType(UserType.APPLE)
                .build();

    }

    public PublicKey getPublicKey(ApplePublicKeyResponse.Key key) {

        byte[] nBytes = Base64.getUrlDecoder().decode(key.getN());
        byte[] eBytes = Base64.getUrlDecoder().decode(key.getE());

        BigInteger n = new BigInteger(1, nBytes);
        BigInteger e = new BigInteger(1, eBytes);

        try {
            RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(n, e);
            KeyFactory keyFactory = KeyFactory.getInstance(key.getKty());
            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
            return publicKey;

        } catch (Exception ex) {
            throw new BusinessException(ex.getMessage());
        }
    }


}
