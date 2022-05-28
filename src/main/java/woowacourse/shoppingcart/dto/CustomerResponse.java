package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Customer;

public class CustomerResponse {

    private String loginId;
    private String userName;

    private CustomerResponse() {
    }

    public CustomerResponse(String loginId, String userName) {
        this.loginId = loginId;
        this.userName = userName;
    }

    public static CustomerResponse of(Customer customer) {
        return new CustomerResponse(customer.getLoginId(), customer.getUserName());
    }

    public String getLoginId() {
        return loginId;
    }

    public String getUserName() {
        return userName;
    }
}
