package com.cmc.finder.api.auth.login.service.apple;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "appleClient", url = "https://appleid.apple.com/auth")
public interface AppleFeignClient {

    @GetMapping(value = "/keys", consumes = "application/json")
    ApplePublicKeyResponse getAppleAuthPublicKey();

}
