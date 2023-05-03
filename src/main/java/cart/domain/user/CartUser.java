package cart.domain.user;

public class CartUser {
    private final UserEmail userEmail;
    private final String password;

    public CartUser(UserEmail userEmail, String password) {
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
