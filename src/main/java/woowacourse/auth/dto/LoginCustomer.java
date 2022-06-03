package woowacourse.auth.dto;

import woowacourse.shoppingcart.domain.Customer;

public class LoginCustomer {

    private String loginId;

    public LoginCustomer(String loginId) {
        this.loginId = loginId;
    }

    public String getLoginId() {
        return loginId;
    }
}
