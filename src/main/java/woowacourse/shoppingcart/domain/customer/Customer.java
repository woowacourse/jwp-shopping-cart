package woowacourse.shoppingcart.domain.customer;

import woowacourse.shoppingcart.exception.InvalidCustomerPropertyException;

public class Customer {

    private Long id;
    private Email email;
    private Username username;
    private Password password;

    public Customer(String email, String username, String password) {
        this(null, email, username, password);
    }

    public Customer(Long id, String email, String username, String password) {
        this.id = id;
        try {
            this.email = new Email(email);
            this.username = new Username(username);
            this.password = new Password(password);
        } catch (IllegalArgumentException e) {
            throw new InvalidCustomerPropertyException(e.getMessage());
        }
    }

    public Customer() {
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
