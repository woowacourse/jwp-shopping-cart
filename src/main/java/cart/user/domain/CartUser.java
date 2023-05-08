package cart.user.domain;

public class CartUser {
    private final UserEmail userEmail;
    private final String password;

    public CartUser(final UserEmail userEmail, final String password) {
        this.userEmail = userEmail;
        this.password = password;
    }

    public String getUserEmail() {
        return userEmail.getEmail();
    }

    public String getPassword() {
        return password;
    }
}
