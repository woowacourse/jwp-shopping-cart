package woowacourse.shoppingcart.domain.customer;

public class Customer {

    private final Long id;
    private final Email email;
    private final Password password;
    private final Username username;

    public Customer(Email email, Password password, Username username) {
        this(null, email, password, username);
    }

    public Customer(Long id, Email email, Password password, Username username) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public Email getEmail() {
        return email;
    }

    public Username getUsername() {
        return username;
    }

    public Password getPassword() {
        return password;
    }

    public boolean isDifferentPassword(Password other) {
        return !this.password.isSame(other);
    }
}
