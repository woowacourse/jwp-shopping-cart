package cart.domain.user;

public final class User {

    private final UserEmail userEmail;
    private final UserPassword userPassword;

    public User(final String emailAddress, final String password) {
        this.userEmail = new UserEmail(emailAddress);
        this.userPassword = new UserPassword(password);
    }
}
