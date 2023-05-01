package cart.controller;

public class Credentials {

    private final String email;
    private final String password;

    public Credentials(final String email, final String password) {
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
