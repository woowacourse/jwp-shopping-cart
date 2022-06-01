package woowacourse.shoppingcart.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import woowacourse.shoppingcart.util.StringUtil;

public class Password {

    private static final Pattern PASSWORD_REGEX = Pattern.compile("^[a-zA-Z\\d!@#$%^*]+$");
    private static final String WRONG_FORMAT_EXCEPTION = "비밀번호는 영문 대소문자, 숫자, 특수문자(!@#$%^*) 조합이여야 합니다.";
    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int MAX_PASSWORD_LENGTH = 20;

    private final String value;

    public Password(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        StringUtil.validateLength(value, MIN_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH);
        validateFormat(value);
    }

    private void validateFormat(String value) {
        Matcher matcher = PASSWORD_REGEX.matcher(value);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(WRONG_FORMAT_EXCEPTION);
        }
    }

    public String getValue() {
        return value;
    }
}
