package woowacourse.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserNameValidator implements ConstraintValidator<UserNameCheck, String> {

    private static final String BLANK_REGEX = "\\S+";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        if (!value.matches(BLANK_REGEX)) {
            return false;
        }
        return true;
    }
}
