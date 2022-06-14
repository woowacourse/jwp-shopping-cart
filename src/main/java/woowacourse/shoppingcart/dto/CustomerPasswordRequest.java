package woowacourse.shoppingcart.dto;

import javax.validation.constraints.NotEmpty;

public class CustomerPasswordRequest {

    @NotEmpty(groups = Request.allProperties.class)
    private String password;

    private CustomerPasswordRequest() {
    }

    public CustomerPasswordRequest(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
