package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.customer.Customer;

public class AuthorizedCustomer {

    private final Long id;
    private final String username;
    private final String email;
    private final String password;

    public AuthorizedCustomer(final Long id, final String username, final String email, final String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Long getId() {
        return id;
    }

    public Customer toCustomer() {
        return new Customer(username, email, password);
    }
}
