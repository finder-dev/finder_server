package com.cmc.finder.domain.debate.constant;

import com.cmc.finder.domain.question.constant.OrderBy;

public enum Option {
    A, B;

    public static Option from(String option) {

        return Option.valueOf(option.toUpperCase());
    }

    public static Boolean equal(Option option1, Option option2){

        return option1.name().equals(option2.name());
    }

}
