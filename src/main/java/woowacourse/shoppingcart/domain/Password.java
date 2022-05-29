package woowacourse.shoppingcart.domain;

import java.util.regex.Pattern;
import woowacourse.shoppingcart.exception.InvalidPasswordException;

public class Password {

    private static final int MIN_THRESHOLD = 8;
    private static final int MAX_THRESHOLD = 20;
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("([0-9]+[a-zA-Z]+[!@#$%^&*()]+)");

    private final String value;

    public Password(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        validateLength(value);
        validateForm(value);
    }

    private void validateLength(String value) {
        if (!isValidLength(value)) {
            throw new InvalidPasswordException(
                    String.format("길이가 부적합합니다(%d)", value.length())
            );
        }
    }

    private boolean isValidLength(String value) {
        return MIN_THRESHOLD <= value.length() && value.length() <= MAX_THRESHOLD;
    }

    private void validateForm(String value) {
        if (!PASSWORD_PATTERN.matcher(value).matches()) {
            throw new InvalidPasswordException("형식이 올바르지 않습니다.");
        }
    }
}
