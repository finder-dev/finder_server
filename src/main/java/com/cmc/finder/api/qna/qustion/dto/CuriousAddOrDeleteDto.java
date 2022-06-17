package com.cmc.finder.api.qna.qustion.dto;

import com.cmc.finder.api.auth.logout.dto.LogoutRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class CuriousAddOrDeleteDto {

    private String message;

    public static CuriousAddOrDeleteDto of(Boolean addOrDelete) {

        // add
        if (addOrDelete) {
            return CuriousAddOrDeleteDto.builder()
                    .message("add success")
                    .build();
        }
        // delete
        return CuriousAddOrDeleteDto.builder()
                .message("delete success")
                .build();

    }
}
