package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Customer;

public class CustomerSaveRequest {

    private final String name;
    private final String email;
    private final String password;

    public CustomerSaveRequest(final String name, final String email, final String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Customer toEntity() {
        return new Customer(name, email, password);
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
