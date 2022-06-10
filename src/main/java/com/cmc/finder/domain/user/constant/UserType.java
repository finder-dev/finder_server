package com.cmc.finder.domain.user.constant;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum UserType {

    GENERAL, KAKAO, APPLE;

    public static boolean isUserType(String type) {

        List<UserType> collect = Arrays.stream(UserType.values())
                .filter(userType -> !userType.equals("GENERAL"))
                .filter(userType -> userType.name().equals(type))
                .collect(Collectors.toList());

        return collect.size() != 0;

    }

    public static UserType from(String type) {
        return UserType.valueOf(type.toUpperCase());
    }


}
