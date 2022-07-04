package com.cmc.finder.domain.model;

public enum OrderBy {

    ANSWER_COUNT, VIEW_COUNT, CREATE_TIME;


    public static OrderBy from(String orderBy) {

        return OrderBy.valueOf(orderBy.toUpperCase());
    }
}
