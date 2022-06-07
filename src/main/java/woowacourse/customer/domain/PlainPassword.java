package woowacourse.customer.domain;

import java.util.regex.Pattern;

import woowacourse.customer.exception.InvalidPasswordException;

public class PlainPassword {

    private static final int MIN_LENGTH = 8;
    private static final int MAX_LENGTH = 20;
    private static final Pattern PATTERN = Pattern.compile("^[0-9a-zA-Z]*$");

    private final String value;

    public PlainPassword(final String value) {
        validateLength(value);
        validatePattern(value);

        this.value = value;
    }

    private void validateLength(final String value) {
        if (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH) {
            throw new InvalidPasswordException("비밀번호의 길이는 " + MIN_LENGTH + "자 이상 " + MAX_LENGTH + "자 이하여야 합니다.");
        }
    }

    private void validatePattern(final String value) {
        if (!PATTERN.matcher(value).find()) {
            throw new InvalidPasswordException("비밀번호는 영어와 숫자로 이루어져야 합니다.");
        }
    }

    public String getValue() {
        return value;
    }
}
