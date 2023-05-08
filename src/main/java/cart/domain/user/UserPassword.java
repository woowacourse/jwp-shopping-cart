package cart.domain.user;

import java.util.Objects;

public class UserPassword {
    private static final int MIN_EMAIL_LENGTH = 1;
    private static final int MAX_EMAIL_LENGTH = 32;
    private static final String PASSWORD_LENGTH_ERROR_MESSAGE = "비밀번호의 길이는 " + MIN_EMAIL_LENGTH + "자 이상 " + MAX_EMAIL_LENGTH + "자 이하입니다.";

    private final String password;

    public UserPassword(final String password) {
        validate(password);
        this.password = password;
    }

    private void validate(final String password) {
        if (password == null) {
            throw new IllegalArgumentException(PASSWORD_LENGTH_ERROR_MESSAGE);
        }
        if (password.length() < MIN_EMAIL_LENGTH || MAX_EMAIL_LENGTH < password.length()) {
            throw new IllegalArgumentException(PASSWORD_LENGTH_ERROR_MESSAGE);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(password);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final UserPassword that = (UserPassword) o;
        return Objects.equals(password, that.password);
    }

    public String getValue() {
        return password;
    }
}
