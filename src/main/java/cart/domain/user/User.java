package cart.domain.user;

public final class User {

    private final String email;
    private final String password;

    public User(final String email, final String password) {
        this.email = email;
        this.password = password;
    }
}
