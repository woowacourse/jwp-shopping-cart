package woowacourse.shoppingcart.dto;

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
