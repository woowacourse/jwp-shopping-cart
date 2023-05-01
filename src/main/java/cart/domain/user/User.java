package cart.domain.user;

public final class User {

    private final Long id;
    private final UserEmail userEmail;
    private final UserPassword userPassword;

    public User(final String emailAddress, final String password) {
        this(null, emailAddress, password);
    }

    public User(final Long id, final String emailAddress, final String password) {
        this.id = id;
        this.userEmail = new UserEmail(emailAddress);
        this.userPassword = new UserPassword(password);
    }

    public Long getId() {
        return id;
    }

    public String getUserEmailValue() {
        return userEmail.getUserEmail();
    }

    public String getUserPasswordValue() {
        return userPassword.getUserPassword();
    }
}
