package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.customer.Customer;

public class CustomerResponse {

    private String email;
    private String username;

    public CustomerResponse() {
    }

    public CustomerResponse(String email, String username) {
        this.email = email;
        this.username = username;
    }

    public static CustomerResponse of(Customer customer) {
        return new CustomerResponse(customer.getEmail().getValue(),
                customer.getUsername().getValue());
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }
}
