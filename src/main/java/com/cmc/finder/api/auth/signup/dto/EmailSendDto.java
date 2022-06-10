package com.cmc.finder.api.auth.signup.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class EmailSendDto {

    private String message;

    public static EmailSendDto of() {

        return EmailSendDto.builder()
                .message("메일을 전송하였습니다.")
                .build();

    }
}
