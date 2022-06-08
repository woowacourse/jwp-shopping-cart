package woowacourse.shoppingcart.domain.customer;

import java.util.Objects;
import java.util.regex.Pattern;
import woowacourse.exception.InvalidPasswordFormatException;

public class Password {

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{10,}");

    private final String password;

    public Password(final String password) {
        validateEmail(password);
        this.password = password;
    }

    private void validateEmail(final String email) {
        if (isPasswordOutOfForm(email)) {
            throw new InvalidPasswordFormatException();
        }
    }

    private boolean isPasswordOutOfForm(final String email) {
        return !PASSWORD_PATTERN.matcher(email).matches();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Password password1 = (Password) o;
        return Objects.equals(password, password1.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(password);
    }

    public String getPassword() {
        return password;
    }
}
