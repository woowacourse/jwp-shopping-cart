package cart.domain.user;

public class User {

    private final Long userId;
    private final UserEmail email;
    private final UserPassword password;

    public User(String email, String password) {
        this(null, email, password);
    }

    public User(Long userId, String email, String password) {
        this.userId = userId;
        this.email = new UserEmail(email);
        this.password = new UserPassword(password);
    }

    public Long getUserId() {
        return userId;
    }

    public String getEmail() {
        return email.getValue();
    }

    public String getPassword() {
        return password.getValue();
    }
}
