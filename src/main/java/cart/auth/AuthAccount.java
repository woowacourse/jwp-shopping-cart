package cart.auth;

public class AuthAccount {

    private final String email;
    private final String password;

    public AuthAccount(final String email, final String password) {
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
