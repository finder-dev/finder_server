package com.cmc.finder.domain.message.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class QueryDto {

    private Long userId;

    private LocalDateTime createTime;

    public QueryDto(Long userId, LocalDateTime createTime) {
        this.userId = userId;
        this.createTime = createTime;
    }
}
