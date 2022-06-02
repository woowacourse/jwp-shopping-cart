package woowacourse.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordCheck, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        if (value.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*") || !value.matches("\\S+")) {
            return false;
        }
        return true;
    }
}
