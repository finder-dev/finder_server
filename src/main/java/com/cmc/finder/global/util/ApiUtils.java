package com.cmc.finder.global.util;

import com.cmc.finder.global.response.ApiResult;
import com.cmc.finder.global.error.ErrorResponse;


public class ApiUtils {

    private ApiUtils() {
        throw new AssertionError();
    }

    public static <T> ApiResult<T> success(T response) {
        return new ApiResult<>(true, response, null);
    }

    public static <T> ApiResult<T> error(ErrorResponse errorResponse){
        return new ApiResult<>(false, null, errorResponse);
    }
}