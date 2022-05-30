package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Customer;

public class SignUpRequest {

    private final String username;
    private final String password;

    public SignUpRequest(final String username, final String password) {
        this.username = username;
        this.password = password;
    }

    public Customer toEntity() {
        return new Customer(getUsername(), getPassword());
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
