package woowacourse.shoppingcart.customer.application.dto.request;

import javax.validation.constraints.NotNull;

public class CustomerRemoveRequest {

    @NotNull
    private String password;

    public CustomerRemoveRequest() {
    }

    public CustomerRemoveRequest(final String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
