package woowacourse.shoppingcart.domain;

import java.util.Objects;
import java.util.regex.Pattern;
import org.springframework.security.crypto.bcrypt.BCrypt;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.shoppingcart.exception.IllegalPasswordException;
import woowacourse.shoppingcart.exception.InvalidLoginException;

public class Password {

    private static final String REGEX = "^(?=.*\\d)(?=.*[a-zA-Z])[0-9a-zA-Z]{8,20}";

    private final String value;

    private Password(String value) {
        this.value = value;
    }

    public static Password defaultPassword(String value) {
        return new Password(value);
    }

    public static Password hashPassword(String value) {
        if (!Pattern.matches(REGEX, value)) {
            throw new IllegalPasswordException();
        }
        String encryptedPassword = BCrypt.hashpw(value, BCrypt.gensalt());
        return new Password(encryptedPassword);
    }

    public String getValue() {
        return value;
    }

    public boolean isSamePassword(String password) {
        return BCrypt.checkpw(password, this.value);
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
