package woowacourse.shoppingcart.domain;

import java.util.Objects;
import woowacourse.exception.PasswordLengthException;

public class Password {

    private static final int MIN_SIZE = 8;
    private static final int MAX_SIZE = 15;

    private final String password;

    public Password(String password) {
        if (password.length() < MIN_SIZE || password.length() > MAX_SIZE) {
            throw new PasswordLengthException();
        }
        this.password = password;
    }

    public boolean isMatches(Password inputPassword, Encoder passwordEncoder) {
        return passwordEncoder.matches(inputPassword.getPassword(), password);
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Password)) {
            return false;
        }
        Password password1 = (Password) o;
        return Objects.equals(password, password1.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(password);
    }
}
