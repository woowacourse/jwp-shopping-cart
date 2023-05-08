package cart.web.dto.request;

public class AuthorizedUserRequest {
    private final String email;
    private final String password;

    public AuthorizedUserRequest(final String email, final String password) {
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
