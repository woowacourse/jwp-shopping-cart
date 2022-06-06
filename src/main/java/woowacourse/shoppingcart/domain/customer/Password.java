package woowacourse.shoppingcart.domain.customer;

import java.util.Objects;
import java.util.regex.Pattern;

public final class Password {
    private static final String passwordRegex = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{4,20}";

    private final String value;

    private Password(String value) {
        validate(value);
        this.value = value;
    }

    public static Password of(String value) {
        return new Password(value);
    }

    public String getValue() {
        return value;
    }

    private void validate(String value) {
        if (!Pattern.matches(passwordRegex, value)) {
            throw new IllegalArgumentException(value + "비밀번호 형식이 올바르지 않습니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Password password = (Password) o;
        return Objects.equals(value, password.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
