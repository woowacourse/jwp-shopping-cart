package woowacourse.shoppingcart.dto.customer;

import javax.validation.constraints.NotBlank;

public class CustomerUpdatePasswordRequest {

    @NotBlank
    private String password;

    private CustomerUpdatePasswordRequest() {
    }

    public CustomerUpdatePasswordRequest(final String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
