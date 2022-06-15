package woowacourse.shoppingcart.domain.customer;

import java.util.regex.Pattern;
import woowacourse.shoppingcart.exception.invalid.InvalidPasswordException;

public class PlainPassword {

    private static final int MIN_LENGTH = 8;
    private static final int MAX_LENGTH = 16;
    private static final Pattern PATTERN = Pattern.compile(
            "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[!@#$%^&*_-])[A-Za-z0-9!@#$%^&*_-]*$");

    private final String value;

    public PlainPassword(final String password) {
        validateLength(password);
        validatePattern(password);
        this.value = password;
    }

    private void validateLength(final String password) {
        if (password.length() < MIN_LENGTH || password.length() > MAX_LENGTH) {
            throw new InvalidPasswordException();
        }
    }

    private void validatePattern(final String password) {
        if (!PATTERN.matcher(password).matches()) {
            throw new InvalidPasswordException();
        }
    }

    public String getValue() {
        return value;
    }
}
