package cart.authorization;

public class AuthorizationInformation {

    private final String email;
    private final String password;

    public AuthorizationInformation(String email, String password) {
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
