package com.cmc.finder.global.response;

import com.cmc.finder.global.error.ErrorResponse;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ApiResult<T>{

    private final boolean success;
    private final T response;
    private final ErrorResponse errorResponse;

    public ApiResult(boolean success, T response, ErrorResponse errorResponse) {

        this.success = success;
        this.response = response;
        this.errorResponse = errorResponse;

    }
}