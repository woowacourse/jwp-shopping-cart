package cart.web.controller.user.dto;

public class UserResponse {
    private final String email;
    private final String password;

    public UserResponse(final String email, final String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
