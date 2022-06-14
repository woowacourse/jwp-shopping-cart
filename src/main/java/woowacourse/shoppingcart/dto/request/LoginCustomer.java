package woowacourse.shoppingcart.dto.request;

import woowacourse.shoppingcart.domain.Customer;

public class LoginCustomer {

    private Long id;
    private String loginId;
    private String username;
    private String password;

    public LoginCustomer() {
    }

    public LoginCustomer(Customer customer) {
        this(customer.getId(), customer.getLoginId(), customer.getUsername(), customer.getPassword());
    }

    public LoginCustomer(Long id, String loginId, String username, String password) {
        this.id = id;
        this.loginId = loginId;
        this.username = username;
        this.password = password;
    }

    public Customer toCustomer() {
        return new Customer(id, loginId, username, password);
    }

    public Long getId() {
        return id;
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
