package com.cmc.finder.infra.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class FcmMessage {

    private Message message;

    public static FcmMessage of(String token, String title, String body, String type) {
        return FcmMessage.builder()
                .message(Message.of(token, title, body, type))
                .build();
    }

    @Builder
    @AllArgsConstructor
    @Getter
    public static class Message {
        private String token;
        private Notification notification;
        private Data data;

        public static Message of(String token, String title, String body, String type) {
            return Message.builder()
                    .token(token)
                    .notification(Notification.of(title, body))
                    .data(Data.of(type))
                    .build();
        }

        @Builder
        @AllArgsConstructor
        @Getter
        public static class Notification {
            private String title;
            private String body;

            public static Notification of(String title, String body) {
                return Notification.builder()
                        .title(title)
                        .body(body)
                        .build();
            }

        }

        @Builder
        @AllArgsConstructor
        @Getter
        public static class Data {
            private String type;

            public static Data of(String type) {
                return Data.builder()
                        .type(type)
                        .build();
            }
        }

    }





}
