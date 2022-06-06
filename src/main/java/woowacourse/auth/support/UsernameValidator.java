package woowacourse.auth.support;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<UsernameCheck, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.contains(" ")) {
            return false;
        }
        return true;
    }
}
