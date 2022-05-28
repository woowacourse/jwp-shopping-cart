package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Customer;

public class LoginCustomer {

    private String loginId;
    private String username;


    public LoginCustomer() {

    }

    public LoginCustomer(Customer customer) {
        this(customer.getLoginId(), customer.getUsername());
    }

    public LoginCustomer(String loginId, String username) {
        this.loginId = loginId;
        this.username = username;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getUsername() {
        return username;
    }
}
