package woowacourse.shoppingcart.dto.customer;

import woowacourse.shoppingcart.domain.customer.Customer;

public class CustomerDetailResponse {

    private String name;
    private String email;

    private CustomerDetailResponse() {
    }

    public CustomerDetailResponse(final String name, final String email) {
        this.name = name;
        this.email = email;
    }

    public static CustomerDetailResponse from(final Customer customer) {
        return new CustomerDetailResponse(customer.getName(), customer.getEmail().getValue());
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
