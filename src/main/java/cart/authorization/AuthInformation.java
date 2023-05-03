package cart.authorization;

public class AuthInformation {

    private final String email;
    private final String password;

    public AuthInformation(String email, String password) {
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
