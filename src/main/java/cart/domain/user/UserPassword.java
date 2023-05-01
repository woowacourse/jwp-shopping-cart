package cart.domain.user;

public final class UserPassword {

    private final String userPassword;

    public UserPassword(final String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserPassword() {
        return userPassword;
    }
}
