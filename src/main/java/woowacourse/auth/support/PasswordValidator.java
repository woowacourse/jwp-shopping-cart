package woowacourse.auth.support;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordCheck, String> {

    private final Pattern pattern = Pattern.compile("[ㄱ-ㅎ]+|[ㅏ-ㅣ]+|[가-힣]+");

    private PasswordValidator() {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.contains(" ")) {
            return false;
        }
        Matcher formMatcher = pattern.matcher(value);
        if (formMatcher.find()) {
            return false;
        }
        return true;
    }
}
