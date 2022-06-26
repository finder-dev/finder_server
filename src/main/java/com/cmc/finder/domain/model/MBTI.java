package com.cmc.finder.domain.model;


import com.cmc.finder.domain.user.constant.UserType;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum MBTI {

    INFJ, INFP, INTJ, INTP, ISFJ, ISFP, ISTJ, ISTP, ENFJ, ENFP, ENTJ, ENTP, ESFJ, ESFP, ESTJ, ESTP;

    public static boolean isMBTI(String isMBTI) {

        List<MBTI> collect = Arrays.stream(MBTI.values())
                .filter(mbti -> mbti.name().equals(isMBTI))
                .collect(Collectors.toList());

        return collect.size() != 0;

    }


    public static MBTI from(String mbti) {

        return MBTI.valueOf(mbti.toUpperCase());
    }

}
