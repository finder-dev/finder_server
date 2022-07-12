package com.cmc.finder.global.advice;

import java.lang.annotation.*;

@Inherited
@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CheckDebateAdmin {
}
