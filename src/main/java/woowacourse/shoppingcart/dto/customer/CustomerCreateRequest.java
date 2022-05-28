package woowacourse.shoppingcart.dto.customer;

import woowacourse.shoppingcart.domain.Customer;

public class CustomerCreateRequest {

    private String email;
    private String username;
    private String password;

    private CustomerCreateRequest() {
    }

    public CustomerCreateRequest(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public Customer toEntity() {
        return new Customer(email, username, password);
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
