package com.cmc.finder.api.auth.signup.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class NicknameCheckDto {

    private String message;

    public static NicknameCheckDto of() {

        return NicknameCheckDto.builder()
                .message("사용 가능한 닉네임입니다.")
                .build();

    }
}
