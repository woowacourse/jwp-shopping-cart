package woowacourse.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordCheck, String> {

    private static final String KOREAN_REGEX = ".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*";
    private static final String BLANK_REGEX = "\\S+";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        if (value.matches(KOREAN_REGEX) || !value.matches(BLANK_REGEX)) {
            return false;
        }
        return true;
    }
}
