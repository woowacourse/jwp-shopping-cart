package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Customer;

public class CustomerDetailServiceResponse {

    private final String name;
    private final String email;

    public CustomerDetailServiceResponse(final String name, final String email) {
        this.name = name;
        this.email = email;
    }

    public static CustomerDetailServiceResponse from(final Customer customer) {
        return new CustomerDetailServiceResponse(customer.getName(), customer.getEmail());
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
