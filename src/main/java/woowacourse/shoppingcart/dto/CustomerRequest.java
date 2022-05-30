package woowacourse.shoppingcart.dto;

import javax.validation.constraints.NotBlank;
import woowacourse.shoppingcart.domain.Customer;

public class CustomerRequest {

    @NotBlank
    private String loginId;

    @NotBlank
    private String name;

    @NotBlank
    private String password;

    private CustomerRequest() {
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
