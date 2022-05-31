package woowacourse.shoppingcart.domain.customer;

import java.util.regex.Pattern;

import woowacourse.shoppingcart.exception.PasswordMisMatchException;

public class Password {

    private static final int MIN_LENGTH = 8;
    private static final int MAX_LENGTH = 20;
    private static final Pattern PATTERN = Pattern.compile("^[0-9a-zA-Z]*$");

    private final String value;

    public Password(final String value) {
        validateLength(value);
        validatePattern(value);

        this.value = value;
    }

    private void validateLength(final String value) {
        if (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("비밀번호의 길이는 " + MIN_LENGTH + "자 이상 " + MAX_LENGTH + "자 이하여야 합니다.");
        }
    }

    private void validatePattern(final String value) {
        if (!PATTERN.matcher(value).find()) {
            throw new IllegalArgumentException("password는 영어와 숫자로 이루어져야 합니다.");
        }
    }

    public void match(final String password) {
        if (!value.equals(password)) {
            throw new PasswordMisMatchException();
        }
    }

    public Password update(final String password) {
        return new Password(password);
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

        Password password = (Password)o;

        return getValue() != null ? getValue().equals(password.getValue()) : password.getValue() == null;
    }

    @Override
    public int hashCode() {
        return getValue() != null ? getValue().hashCode() : 0;
    }
}
