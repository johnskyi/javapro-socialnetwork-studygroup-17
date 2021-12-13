package ru.skillbox.socialnetwork.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;
import java.util.regex.Pattern;

public class NullOrPatternValidator implements ConstraintValidator<NullOrPattern, String> {

    private String pattern;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Objects.isNull(value) || !value.isBlank() && Pattern.compile(pattern).matcher(value).matches();
    }

    @Override
    public void initialize(NullOrPattern constraintAnnotation) {
        pattern = constraintAnnotation.pattern();
    }
}
