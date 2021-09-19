package ru.skillbox.socialnetwork.annotations;

import ru.skillbox.socialnetwork.exceptions.FieldNotValidException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;
import java.util.regex.Pattern;

public class NullOrPatternValidator implements ConstraintValidator<NullOrPattern, String> {

    private String message;
    private String pattern;
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (Objects.isNull(value) || (!value.isBlank() && Pattern.compile(pattern).matcher(value).matches())){
            return true;
        }
        throw new FieldNotValidException(message);
    }

    @Override
    public void initialize(NullOrPattern constraintAnnotation) {
        message = constraintAnnotation.message();
        pattern = constraintAnnotation.pattern();
    }
}
