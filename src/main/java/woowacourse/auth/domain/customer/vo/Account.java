package woowacourse.auth.domain.customer.vo;

import java.util.Objects;
import java.util.regex.Pattern;

public class Account {

    private static final Pattern FORMAT = Pattern.compile("^[0-9a-z]*$");
    private static final int MIN_LENGTH = 4;
    private static final int MAX_LENGTH = 15;

    private final String value;

    public Account(String value) {
        value = value.trim();
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        validateFormat(value);
        validateLength(value);
    }

    private void validateFormat(String value) {
        if (!FORMAT.matcher(value).matches()) {
            throw new IllegalArgumentException(
                    String.format("계정은 소문자와 숫자로 생성 가능합니다. 입력값: %s", value));
        }
    }

    private void validateLength(String value) {
        if (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(
                    String.format("계정은 %d ~ %d자로 생성 가능합니다. 입력값: %s",
                            MIN_LENGTH, MAX_LENGTH, value));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Account)) {
            return false;
        }
        Account account = (Account) o;
        return Objects.equals(value, account.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
