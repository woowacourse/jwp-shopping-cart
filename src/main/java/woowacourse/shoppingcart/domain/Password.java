package woowacourse.shoppingcart.domain;

import java.util.Objects;
import org.springframework.security.crypto.bcrypt.BCrypt;
import woowacourse.shoppingcart.exception.badrequest.InvalidPasswordException;

public class Password {

    private static final String PLAIN_PASSWORD_PATTERN = "^(?=.*\\d)(?=.*[a-zA-Z])[0-9a-zA-Z]{8,20}";
    private static final String HASH_PASSWORD_PATTERN = "^\\$2a\\$10\\$.{22}.{31}$";
    private final String value;

    private Password(final String value) {
        this.value = value;
    }

    public static Password fromPlain(final String value) {
        if (!value.matches(PLAIN_PASSWORD_PATTERN)) {
            throw new InvalidPasswordException();
        }
        final String hashValue = BCrypt.hashpw(value, BCrypt.gensalt());
        return new Password(hashValue);
    }

    public static Password fromHash(final String value) {
        if (!value.matches(HASH_PASSWORD_PATTERN)) {
            throw new InvalidPasswordException();
        }
        return new Password(value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Password password = (Password) o;
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
