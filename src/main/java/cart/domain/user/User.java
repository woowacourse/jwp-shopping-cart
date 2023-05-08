package cart.domain.user;

public class User {

    private final Long id;
    private final UserEmail email;
    private final UserPassword password;

    public User(final String email, final String password) {
        this(null, email, password);
    }

    public User(final Long userId, final String email, final String password) {
        this.id = userId;
        this.email = new UserEmail(email);
        this.password = new UserPassword(password);
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email.getValue();
    }

    public String getPassword() {
        return password.getValue();
    }
}
