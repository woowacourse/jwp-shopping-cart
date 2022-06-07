package woowacourse.shoppingcart.customer.domain;

import java.util.Objects;
import java.util.regex.Pattern;
import org.springframework.security.crypto.bcrypt.BCrypt;
import woowacourse.shoppingcart.customer.exception.badrequest.InvalidPasswordException;

public class Password {

    private static final Pattern PLAIN_PASSWORD_PATTERN = Pattern.compile("^(?=.*\\d)(?=.*[a-zA-Z])[0-9a-zA-Z]{8,20}");
    private static final Pattern HASH_PASSWORD_PATTERN = Pattern.compile("^\\$2a\\$10\\$.{22}.{31}$");

    private final String value;

    private Password(final String value) {
        this.value = value;
    }

    public static Password fromPlain(final String value) {
        if (!PLAIN_PASSWORD_PATTERN.matcher(value).matches()) {
            throw new InvalidPasswordException();
        }
        final String hashValue = BCrypt.hashpw(value, BCrypt.gensalt());
        return new Password(hashValue);
    }

    public static Password fromHash(final String value) {
        if (!HASH_PASSWORD_PATTERN.matcher(value).matches()) {
            throw new InvalidPasswordException();
        }
        return new Password(value);
    }

    public boolean isSame(final String plainValue) {
        return BCrypt.checkpw(plainValue, value);
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
