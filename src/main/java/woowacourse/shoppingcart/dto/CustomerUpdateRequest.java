package woowacourse.shoppingcart.dto;

import javax.validation.constraints.NotEmpty;
import woowacourse.shoppingcart.domain.Customer;

public class CustomerUpdateRequest {

    @NotEmpty(groups = Request.allProperties.class)
    private String name;
    @NotEmpty(groups = Request.allProperties.class)
    private String password;

    public CustomerUpdateRequest() {
    }

    public CustomerUpdateRequest(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public Customer toCustomer(String loginId) {
        return new Customer(loginId, name, password);
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
