package com.cmc.finder.global.aspect;

import java.lang.annotation.*;

@Inherited
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CheckCommunityAdmin {
}
