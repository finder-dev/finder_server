package com.cmc.finder.infra.notification;

import com.cmc.finder.infra.notification.exception.NotificationFailedException;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FCMService {
    //TODO interface 생성
    private final FCMClient fcmClient;

    private final String CONTENT_TYPE = "application/json; UTF-8";

    public void sendMessageTo(String targetToken, String title, String body) {

        try {
            FCMMessage message = makeMessage(targetToken, title, body);
            fcmClient.requestNotification(CONTENT_TYPE, "Bearer " + getAccessToken(), message);
        } catch (IOException e) {
            throw new NotificationFailedException();
        }

    }

    private FCMMessage makeMessage(String targetToken, String title, String body) {
        //TODO 변경
        FCMMessage fcmMessage = FCMMessage.builder()
                .message(FCMMessage.Message.builder()
                        .token(targetToken)
                        .notification(FCMMessage.Notification.builder()
                                .title(title)
                                .body(body)
                                .image(null)
                                .build()
                        ).build()).validateOnly(false).build();

        return fcmMessage;
    }

    private String getAccessToken() throws IOException {
        String firebaseConfigPath = "firebase/firebase_service_key.json";

        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }
}
