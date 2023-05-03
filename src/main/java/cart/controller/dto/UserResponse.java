package cart.controller.dto;

import cart.domain.User;

import java.util.Objects;

public class UserResponse {

    private final String email;
    private final String password;

    private UserResponse(final String email, final String password) {
        this.email = email;
        this.password = password;
    }

    public static UserResponse from (final User user) {
        return new UserResponse(
                user.getEmail(),
                user.getPassword()
        );
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final UserResponse that = (UserResponse) o;
        return Objects.equals(email, that.email) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password);
    }

    @Override
    public String toString() {
        return "UserResoponse{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
