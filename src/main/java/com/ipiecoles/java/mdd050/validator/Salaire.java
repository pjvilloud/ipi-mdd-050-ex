package com.ipiecoles.java.mdd050.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SalaireValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Salaire {
    String message() default "Salaire incorrect";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
