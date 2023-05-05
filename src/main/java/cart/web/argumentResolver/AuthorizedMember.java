package cart.web.argumentResolver;

public class AuthorizedMember {

    private final String email;
    private final String password;

    public AuthorizedMember(final String email, final String password) {
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
