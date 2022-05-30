package woowacourse.auth.domain;

import java.util.Objects;
import java.util.regex.Pattern;

public class Account {

    private static final Pattern FORMAT = Pattern.compile("^[0-9a-z]*$");

    private final String value;

    public Account(String value) {
        value = value.trim();
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        if (!FORMAT.matcher(value).matches()) {
            throw new IllegalArgumentException(
                    String.format("계정은 소문자와 숫자로 생성 가능합니다. 입력값: %s", value));
        }
        if (value.length() < 4 || value.length() > 15) {
            throw new IllegalArgumentException(
                    String.format("계정은 4 ~ 15자로 생성 가능합니다. 입력값: %s", value));
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
