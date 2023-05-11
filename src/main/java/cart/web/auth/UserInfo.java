package cart.web.auth;

public class UserInfo {
    private final String email;
    private final String password;

    public UserInfo(String email, String password) {
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
