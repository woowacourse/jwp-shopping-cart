package cart.domain.member;

import cart.exception.PasswordCreateFailException;

import java.util.Objects;

public class Password {

    private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$";

    private final String password;

    public Password(final String password) {
        validate(password);
        this.password = password;
    }

    private void validate(final String password) {
        if (password.isBlank()) {
            throw new PasswordCreateFailException();
        }

        if (!password.matches(PASSWORD_REGEX)) {
            throw new PasswordCreateFailException();
        }
    }

    public boolean isSamePassword(final String password) {
        return Objects.equals(this.password, password);
    }

    public String getPassword() {
        return password;
    }
}
