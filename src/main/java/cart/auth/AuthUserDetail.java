package cart.auth;

public class AuthUserDetail {
    private String email;
    private String password;

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