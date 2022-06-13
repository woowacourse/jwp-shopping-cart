package woowacourse.shoppingcart.domain.customer;

public class Customer {

    private final Long id;
    private final Email email;
    private final Username username;
    private final EncodedPassword password;

    public Customer(String email, String username, EncodedPassword password) {
        this(null, email, username, password);
    }

    public Customer(Long id, String email, String username, EncodedPassword password) {
        this(id, new Email(email), new Username(username), password);
    }

    public Customer(Long id, Email email, Username username, EncodedPassword password) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public boolean isSamePassword(EncodedPassword otherPassword) {
        return password.equals(otherPassword);
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email.getValue();
    }

    public String getUsername() {
        return username.getValue();
    }

    public String getPassword() {
        return password.getValue();
    }
}
