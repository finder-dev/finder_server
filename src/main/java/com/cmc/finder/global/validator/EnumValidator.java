package com.cmc.finder.global.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<Enum, String> {

    private Enum annotation;

    @Override
    public void initialize(Enum constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean result = false;

        if (value == null && this.annotation.ignoreCase()) {
            result = true;
        } else if (value == null) {
            result = false;
        } else {
            Object[] enumValues = this.annotation.enumClass().getEnumConstants();
            if (enumValues != null) {
                for (Object enumValue : enumValues) {

                    if (value.equals(enumValue.toString())) {
                        result = true;
                        break;
                    }
                }

            }
        }

        return result;
    }

}