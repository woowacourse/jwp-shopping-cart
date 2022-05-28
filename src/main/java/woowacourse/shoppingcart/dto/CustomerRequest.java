package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Customer;

public class CustomerRequest {

    private String loginId;
    private String username;
    private String password;

    public CustomerRequest() {
    }

    public CustomerRequest(String loginId, String username, String password) {
        this.loginId = loginId;
        this.username = username;
        this.password = password;
    }

    public static Customer toCustomer(CustomerRequest customerRequest) {
        return new Customer(null, customerRequest.getLoginId(), customerRequest.getUsername(),
                customerRequest.getPassword());
    }

    public String getLoginId() {
        return loginId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
