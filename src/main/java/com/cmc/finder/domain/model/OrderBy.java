package com.cmc.finder.domain.model;

public enum OrderBy {

    ANSWER_COUNT,  CREATE_TIME;
//TODO 이후 추가 -> VIEW_COUNT
    public static OrderBy from(String orderBy) {

        return OrderBy.valueOf(orderBy.toUpperCase());
    }
}
