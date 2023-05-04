package cart.dto;

public class AuthInfoRequest {

    private final String email;
    private final String password;

    public AuthInfoRequest(final String email, final String password) {
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
