package cart.domain.user;

public class User {

    private final Email email;
    private final Password password;

    public User(final String address, final String password) {
        this.email = new Email(address);
        this.password = new Password(password);
    }

    public String getEmail() {
        return email.getAddress();
    }

    public String getPassword() {
        return password.getValue();
    }
}
