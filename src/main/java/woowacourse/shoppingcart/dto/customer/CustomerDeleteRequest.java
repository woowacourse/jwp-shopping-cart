package woowacourse.shoppingcart.dto.customer;

import javax.validation.constraints.NotBlank;

public class CustomerDeleteRequest {

    @NotBlank
    private String password;

    public CustomerDeleteRequest() {
    }

    public CustomerDeleteRequest(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
