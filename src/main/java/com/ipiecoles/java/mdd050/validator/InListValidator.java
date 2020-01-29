package com.ipiecoles.java.mdd050.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InListValidator implements ConstraintValidator<InList, Object> {

    List<String> valueList = new ArrayList<>();

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return valueList.contains(value.toString());
    }

    @Override
    public void initialize(InList constraintAnnotation) {
        valueList = Arrays.asList(constraintAnnotation.list().split(","));
    }
}