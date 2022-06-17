package com.cmc.finder.domain.question.constant;

import com.cmc.finder.global.config.InvalidValueException;
import com.cmc.finder.global.error.exception.ErrorCode;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum OrderBy {

    VIEW_COUNT, CREATE_TIME;

    public static void isOrderBy(String order) {

        List<OrderBy> collect = Arrays.stream(OrderBy.values())
                .filter(orderBy -> orderBy.name().equals(order))
                .collect(Collectors.toList());
        if (collect.size() == 0) {
            throw new InvalidValueException(ErrorCode.INVALID_FILTER_TYPE);
        }
    }

    public static OrderBy from(String orderBy) {

        return OrderBy.valueOf(orderBy.toUpperCase());
    }
}
