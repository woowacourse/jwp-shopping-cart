package woowacourse.shoppingcart.domain.customer;

public class Customer {

    private final Long id;
    private final Email email;
    private final Username username;
    private final String password;

    public Customer(String email, String username, String password) {
        this(null, email, username, password);
    }

    public Customer(Long id, String email, String username, String password) {
        this(id, new Email(email), new Username(username), password);
    }

    public Customer(Long id, Email email, Username username, String password) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
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
        return password;
    }
}
