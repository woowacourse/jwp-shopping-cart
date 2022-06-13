package woowacourse.shoppingcart.dto.customer;

import woowacourse.shoppingcart.domain.Customer;

public class CustomerResponse {

    private String loginId;
    private String name;

    private CustomerResponse() {
    }

    public CustomerResponse(String loginId, String name) {
        this.loginId = loginId;
        this.name = name;
    }

    public static CustomerResponse of(Customer customer) {
        return new CustomerResponse(customer.getLoginId(), customer.getName());
    }

    public String getLoginId() {
        return loginId;
    }

    public String getName() {
        return name;
    }
}
