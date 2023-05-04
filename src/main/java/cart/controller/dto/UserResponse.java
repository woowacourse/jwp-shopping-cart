package cart.controller.dto;

import cart.domain.User;

public class UserResponse {

    private final String email;
    private final String password;

    private UserResponse(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static UserResponse from(final User user) {
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
}
