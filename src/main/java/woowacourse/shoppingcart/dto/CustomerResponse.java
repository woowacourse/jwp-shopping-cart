package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Customer;

public class CustomerResponse {
    private String userName;

    private CustomerResponse() {
    }

    public CustomerResponse(final Customer customer) {
        this.userName = customer.getUserName();
    }

    public String getUserName() {
        return userName;
    }
}
