package com.ipiecoles.java.mdd050.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = InListValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@ReportAsSingleViolation
public @interface InList {

    String message() default "ne fait pas partie des valeurs admises";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String list() default "";
}