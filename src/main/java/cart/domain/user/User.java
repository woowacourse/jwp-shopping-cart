package cart.domain.user;

public class User {

    private final Long id;
    private final Email email;
    private final Password password;

    public User(final Long id, final Email email, final Password password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email.getEmail();
    }

    public String getPassword() {
        return password.getPassword();
    }
}
