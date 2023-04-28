package cart.domain.user;

public class User {

    private final Long id;
    private final UserEmail userEmail;
    private final UserPassword userPassword;

    public User(final Long id, final String email, final String password) {
        this.id = id;
        userEmail = new UserEmail(email);
        userPassword = new UserPassword(password);
    }

    public User(final String email, final String password) {
        this(null, email, password);
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return userEmail.getEmail();
    }

    public String getPassword() {
        return userPassword.getPassword();
    }
}
