package cart.domain.user;

import java.util.Objects;

public class UserPassword {

    private final String password;

    public UserPassword(String password) {
        this.password = password;
    }

    @Override
    public int hashCode() {
        return Objects.hash(password);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPassword that = (UserPassword) o;
        return Objects.equals(password, that.password);
    }

    public String getValue() {
        return password;
    }
}
