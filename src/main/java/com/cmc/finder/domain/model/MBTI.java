package com.cmc.finder.domain.model;

import com.cmc.finder.domain.user.constant.UserType;
import com.cmc.finder.global.config.InvalidValueException;
import com.cmc.finder.global.error.exception.ErrorCode;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum MBTI {

    INFJ, INFP, INTJ, INTP, ISFJ, ISFP, ISTJ, ISTP, ENFJ, ENFP, ENTJ, ENTP, ESFJ, ESFP, ESTJ, ESTP;

    public static MBTI from(String mbti) {

        return MBTI.valueOf(mbti.toUpperCase());
    }

}
