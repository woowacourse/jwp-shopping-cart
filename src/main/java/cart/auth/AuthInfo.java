package cart.auth;

public class AuthInfo {

    private static final AuthInfo NON_AUTHORIZE = new AuthInfo("", "");

    private final String email;
    private final String password;

    public AuthInfo(String email, String password) {
        this.email = email;
        this.password = password;
    }

    static AuthInfo nonAuthorize() {
        return NON_AUTHORIZE;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
