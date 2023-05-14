package cart.dto;

import cart.entity.User;

public final class UserResponse {
    private final Long id;
    private final String email;
    private final String password;

    public UserResponse(final Long id, final String email, final String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public static UserResponse from(User user) {
        return new UserResponse(user.getId(), user.getEmail(), user.getPassword());
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
