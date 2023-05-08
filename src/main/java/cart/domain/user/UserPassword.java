package cart.domain.user;

import java.util.Objects;

public final class UserPassword {

    private final String userPassword;

    public UserPassword(final String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserPassword() {
        return userPassword;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final UserPassword other = (UserPassword) o;
        return Objects.equals(userPassword, other.userPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userPassword);
    }
}
