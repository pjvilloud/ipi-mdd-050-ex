package com.ipiecoles.java.mdd050.validator;

import com.ipiecoles.java.mdd050.model.Entreprise;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SalaireValidator implements ConstraintValidator<Salaire, Double> {
    @Override
    public void initialize(Salaire constraintAnnotation) {

    }

    @Override
    public boolean isValid(Double salaire, ConstraintValidatorContext context) {
        return salaire >= Entreprise.SALAIRE_BASE &&
                salaire <= Entreprise.SALAIRE_BASE * 6;
    }
}
