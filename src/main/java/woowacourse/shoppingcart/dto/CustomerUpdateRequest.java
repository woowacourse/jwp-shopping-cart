package woowacourse.shoppingcart.dto;

import javax.validation.constraints.NotBlank;
import woowacourse.shoppingcart.domain.Customer;

public class CustomerUpdateRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String password;


    public CustomerUpdateRequest() {
    }

    public CustomerUpdateRequest(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public Customer toCustomerWith(String loginId) {
        return new Customer(loginId, name, password);
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
