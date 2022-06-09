package woowacourse.shoppingcart.dto.response;

import woowacourse.shoppingcart.domain.Customer;

public class CustomerResponse {

    private final String username;
    private final String email;

    private CustomerResponse(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public static CustomerResponse from(Customer customer) {
        return new CustomerResponse(customer.getUsername().getValue(), customer.getEmail().getValue());
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
