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
public class FcmService {
    private final FcmClient fcmClient;

    private final String CONTENT_TYPE = "application/json; UTF-8";

    public void sendMessageTo(String targetToken, String title, String body, String type) {

        try {
            FcmMessage message = makeMessage(targetToken, title, body, type);
            fcmClient.requestNotification(CONTENT_TYPE, "Bearer " + getAccessToken(), message);
        } catch (IOException e) {
            throw new NotificationFailedException();
        }

    }

    private FcmMessage makeMessage(String targetToken, String title, String body, String type) {
        FcmMessage message = FcmMessage.of(targetToken, title, body, type);
        return message;
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
