package com.cmc.finder.domain.qna.question.constant;

public enum OrderBy {

    VIEW_COUNT, CREATE_TIME;


    public static OrderBy from(String orderBy) {

        return OrderBy.valueOf(orderBy.toUpperCase());
    }
}
