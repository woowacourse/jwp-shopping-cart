package woowacourse.shoppingcart.dto.customer;

import javax.validation.constraints.NotBlank;

public class CustomerUpdateRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String password;

    private CustomerUpdateRequest() {
    }

    public CustomerUpdateRequest(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
