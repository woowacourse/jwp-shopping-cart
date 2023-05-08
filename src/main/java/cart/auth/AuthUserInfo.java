package cart.auth;

public class AuthUserInfo {

    private final String email;

    public AuthUserInfo(final String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
