package woowacourse.shoppingcart.dto.response;

import woowacourse.shoppingcart.domain.customer.Customer;

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
