package woowacourse.shoppingcart.dto;

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
