package io.github.ztoany.versa.infra.springboot.validation;

import io.github.ztoany.versa.infra.springboot.validation.constraint.ValidStringList;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class ValidStringListValidator implements ConstraintValidator<ValidStringList, List<String>>  {
    private String regexp;

    @Override
    public void initialize(ValidStringList constraintAnnotation) {
        this.regexp = constraintAnnotation.regexp();
    }

    @Override
    public boolean isValid(List<String> list, ConstraintValidatorContext context) {
        if (list == null) {
            return true;
        }

        for (String item : list) {
            if (!item.matches(this.regexp)) {
                return false;
            }
        }

        return true;
    }
}
