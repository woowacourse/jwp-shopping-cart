package woowacourse.shoppingcart.domain.customer;

public class Customer {
    private final Long id;
    private final Email email;
    private final Password password;
    private final Username username;

    public Customer(Long id, String email, Password password, String username) {
        this.id = id;
        this.email = new Email(email);
        this.password = password;
        this.username = new Username(username);
    }

    public Customer(String email, Password password, String username) {
        this(null, email, password, username);
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

    public boolean isDifferentPassword(String password) {
        return !this.password.isSame(password);
    }
}
