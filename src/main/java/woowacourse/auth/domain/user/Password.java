package woowacourse.auth.domain.user;

import java.util.Objects;
import woowacourse.auth.exception.format.InvalidPasswordFormatException;

public class Password {

    private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$";
    private final String value;

    public Password(String value) {
        validateFormat(value);
        this.value = value;
    }

    private void validateFormat(String value) {
        if (!value.matches(PASSWORD_REGEX)) {
            throw new InvalidPasswordFormatException();
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

    @Override
    public String toString() {
        return "Password{" +
                "value='" + value + '\'' +
                '}';
    }
}
