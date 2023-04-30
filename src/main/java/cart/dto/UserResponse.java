package cart.dto;

import cart.dao.entity.Users;

public class UserResponse {

    private final String email;
    private final String password;

    public UserResponse(final String email, final String password) {
        this.email = email;
        this.password = password;
    }

    public UserResponse(final Users user) {
        this(user.getEmail(), user.getPassword());
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
