package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Customer;

public class CustomerResponse {

    private String username;
    private String email;

    private CustomerResponse(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public static CustomerResponse from(Customer customer) {
        return new CustomerResponse(customer.getUsername(), customer.getEmail());
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
