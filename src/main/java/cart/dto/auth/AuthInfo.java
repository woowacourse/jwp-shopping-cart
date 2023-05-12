package cart.dto.auth;

public class AuthInfo {

    private String email;
    private String password;

    public AuthInfo(final String email, final String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "AuthInfo{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
