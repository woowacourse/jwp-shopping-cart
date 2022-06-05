package woowacourse.auth.support;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<EmailCheck, String> {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return false;
        }
        return value.matches(EMAIL_REGEX);
    }

    public boolean isValid(String value) {
        if (value == null || value.isBlank()) {
            return false;
        }
        return value.matches(EMAIL_REGEX);
    }
}
