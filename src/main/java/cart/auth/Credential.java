package cart.auth;

public class Credential {

    private final Long id;
    private final String email;
    private final String password;

    public Credential(final String email, final String password) {
        this(null, email, password);
    }

    public Credential(final Long id, final String email, final String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
