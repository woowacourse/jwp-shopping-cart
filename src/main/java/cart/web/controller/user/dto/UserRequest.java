package cart.web.controller.user.dto;

public class UserRequest {

    private final String email;
    private final String password;

    public UserRequest(final String email, final String password) {
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
