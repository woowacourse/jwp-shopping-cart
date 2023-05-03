package cart.domain.user;

public class User {
    private final Email email;
    private final Password password;
    private final Long userId;

    public User(Email email, Password password, Long userId) {
        this.email = email;
        this.password = password;
        this.userId = userId;
    }

    public User(Email email, Password password) {
        this(email, password, null);
    }

    public String getEmail() {
        return email.getEmail();
    }

    public String getPassword() {
        return password.getPassword();
    }
}
