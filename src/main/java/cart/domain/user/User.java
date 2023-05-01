package cart.domain.user;

public class User {

    private final Email email;
    private final String password;

    public User(final String email, final String password) {
        this(new Email(email), password);
    }

    public User(final Email email, final String password) {
        this.email = email;
        this.password = password;
    }

    public Email getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
