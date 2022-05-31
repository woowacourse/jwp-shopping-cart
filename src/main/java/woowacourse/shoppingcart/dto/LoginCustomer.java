package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Customer;

public class LoginCustomer {

    private String loginId;
    private String name;

    public LoginCustomer() {
    }

    public LoginCustomer(Customer customer) {
        this(customer.getLoginId(), customer.getName());
    }

    public LoginCustomer(String loginId, String name) {
        this.loginId = loginId;
        this.name = name;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getName() {
        return name;
    }
}
