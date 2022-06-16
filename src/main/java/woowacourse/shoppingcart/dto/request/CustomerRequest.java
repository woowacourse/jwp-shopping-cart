package woowacourse.shoppingcart.dto.request;

import woowacourse.shoppingcart.domain.Customer;

public class CustomerRequest {

    private String loginId;
    private String name;
    private String password;

    public CustomerRequest() {
    }

    public CustomerRequest(String loginId, String name, String password) {
        this.loginId = loginId;
        this.name = name;
        this.password = password;
    }

    public Customer toCustomer() {
        return new Customer(loginId, name, password);
    }

    public String getLoginId() {
        return loginId;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
