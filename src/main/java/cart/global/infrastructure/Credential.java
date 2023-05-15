package cart.global.infrastructure;

public class Credential {

    private final String email;
    private final String password;

    public Credential(final String email, final String password) {
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
