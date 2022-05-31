package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.dto.CustomerResponse;

public class LoginCustomer {
    private String loginId;
    private String name;

    private LoginCustomer() {
    }

    public LoginCustomer(String loginId, String name) {
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
