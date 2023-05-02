package cart.domain.user;

import java.util.Objects;

public class UserEmail {

    private final String email;

    public UserEmail(String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEmail userEmail = (UserEmail) o;
        return Objects.equals(email, userEmail.email);
    }

    public String getValue() {
        return email;
    }
}
