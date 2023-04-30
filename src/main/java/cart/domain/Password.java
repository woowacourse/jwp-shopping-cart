package cart.domain;

import java.util.Objects;

public class Password {

    private final String password;

    private Password(final String password) {
        this.password = password;
    }

    public static Password password(final String password) {
        return new Password(password);
    }

    public boolean match(final Password password) {
        return equals(password);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Password)) {
            return false;
        }
        final Password password1 = (Password) o;
        return password.equals(password1.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(password);
    }

    public String getPassword() {
        return password;
    }
}
