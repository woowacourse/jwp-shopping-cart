package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Customer;

public class CustomerRequest {

    private String loginId;
    private String userName;
    private String password;

    private CustomerRequest() {
    }

    public CustomerRequest(String loginId, String userName, String password) {
        this.loginId = loginId;
        this.userName = userName;
        this.password = password;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public Customer toCustomer() {
        return new Customer(loginId, userName, password);
    }
}
