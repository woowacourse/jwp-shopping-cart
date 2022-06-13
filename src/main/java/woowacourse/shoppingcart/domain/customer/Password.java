package woowacourse.shoppingcart.domain.customer;

import java.util.Objects;
import woowacourse.shoppingcart.exception.format.InvalidPasswordFormatException;
import woowacourse.shoppingcart.support.PasswordEncoder;

public class Password {
    private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$";

    private final String value;

    public Password(String value) {
        this.value = value;
    }

    public static Password fromPlainText(String value) {
        validateFormat(value);
        return new Password(PasswordEncoder.encrypt(value));
    }

    private static void validateFormat(String value) {
        if (Objects.isNull(value) || !value.matches(PASSWORD_REGEX)) {
            throw new InvalidPasswordFormatException();
        }
    }

    public boolean matches(String plainText) {
        return value.equals(PasswordEncoder.encrypt(plainText));
    }

    public String getValue() {
        return value;
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
