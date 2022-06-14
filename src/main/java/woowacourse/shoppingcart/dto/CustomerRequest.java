package woowacourse.shoppingcart.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import woowacourse.shoppingcart.domain.Customer;

public class CustomerRequest {

    @NotNull(groups = Request.allProperties.class)
    private String loginId;
    @NotEmpty(groups = Request.allProperties.class)
    private String name;
    @NotEmpty(groups = Request.allProperties.class)
    private String password;

    public CustomerRequest() {
    }

    public CustomerRequest(String loginId, String name, String password) {
        this.loginId = loginId;
        this.name = name;
        this.password = password;
    }

    public Customer toCustomer() {
        return new Customer(null, loginId, name, password);
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
