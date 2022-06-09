package woowacourse.shoppingcart.dto.request;

import woowacourse.shoppingcart.domain.Customer;

public class LoginCustomer {

    private String loginId;
    private String username;
    private String password;

    public LoginCustomer() {
    }

    public LoginCustomer(Customer customer) {
        this(customer.getLoginId(), customer.getUsername(), customer.getPassword());
    }

    public LoginCustomer(String loginId, String username, String password) {
        this.loginId = loginId;
        this.username = username;
        this.password = password;
    }

    public Customer toCustomer() {
        return new Customer(null, loginId, username, password);
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
