package woowacourse.customer.domain;

import java.util.regex.Pattern;

import woowacourse.customer.exception.InvalidUsernameException;

public class Username {

    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 15;
    private static final Pattern PATTERN = Pattern.compile("^[0-9a-zA-Z]*$");

    private final String value;

    public Username(final String value) {
        validateLength(value);
        validatePattern(value);

        this.value = value;
    }

    private void validateLength(final String value) {
        if (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH) {
            throw new InvalidUsernameException("사용자 이름의 길이는 " + MIN_LENGTH + "자 이상 " + MAX_LENGTH + "자 이하여야 합니다.");
        }
    }

    private void validatePattern(final String value) {
        if (!PATTERN.matcher(value).find()) {
            throw new InvalidUsernameException("사용자 이름은 영어와 숫자로 이루어져야 합니다.");
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Username username = (Username)o;

        return getValue() != null ? getValue().equals(username.getValue()) : username.getValue() == null;
    }

    @Override
    public int hashCode() {
        return getValue() != null ? getValue().hashCode() : 0;
    }
}
