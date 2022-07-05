package com.cmc.finder.infra.notification;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "FCMClient", url = "https://fcm.googleapis.com/")
public interface FcmClient {

    @PostMapping(value = "/v1/projects/pushtest-a0da7/messages:send", consumes = "application/json; charset=utf-8")

    ResponseEntity<String> requestNotification(
            @RequestHeader("Content-Type") String contentType,
            @RequestHeader("Authorization") String authorization,
            @RequestBody FcmMessage fcmMessage
    );

}
