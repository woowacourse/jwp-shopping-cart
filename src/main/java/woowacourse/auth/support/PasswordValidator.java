package woowacourse.auth.support;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordCheck, String> {

    private static final int MAX_LENGTH = 255;
    private static final int MIN_LENGTH = 6;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank() || !isValidLength(value)) {
            return false;
        }
        if (value.matches("[ㄱ-ㅎ]+|[ㅏ-ㅣ]+|[가-힣]+") || !value.matches("\\S+")) {
            return false;
        }
        return true;
    }

    private boolean isValidLength(String value) {
        return MIN_LENGTH <= value.length() && value.length() <= MAX_LENGTH;
    }
}
