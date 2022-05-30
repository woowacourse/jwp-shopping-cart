package woowacourse.auth.domain.user;

import java.util.Objects;
import org.springframework.security.crypto.password.PasswordEncoder;
import woowacourse.auth.exception.format.InvalidPasswordFormatException;

public class Password {
    private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$";

    private final String value;
    private final PasswordEncoder passwordEncoder;

    public Password(String value, PasswordEncoder passwordEncoder) {
        this.value = value;
        this.passwordEncoder = passwordEncoder;
    }

    public static Password fromPlainText(String value, PasswordEncoder passwordEncoder) {
        validateFormat(value);
        return new Password(passwordEncoder.encode(value), passwordEncoder);
    }

    private static void validateFormat(String value) {
        if (Objects.isNull(value) || !value.matches(PASSWORD_REGEX)) {
            throw new InvalidPasswordFormatException();
        }
    }

    public boolean matches(String plainText) {
        return passwordEncoder.matches(plainText, value);
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
