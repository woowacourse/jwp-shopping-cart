package cart.dto.user;

import cart.domain.user.User;

public class UserResponse {

    private final String email;
    private final String password;

    public UserResponse(final String email, final String password) {
        this.email = email;
        this.password = password;
    }

    public static UserResponse from(final User user) {
        return new UserResponse(user.getEmail().getValue(), user.getPassword().getValue());
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
