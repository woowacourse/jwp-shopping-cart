package cart.auth;

public class AuthUserDetail {
    private final String email;
    private final String password;

    public AuthUserDetail(String email, String password) {
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