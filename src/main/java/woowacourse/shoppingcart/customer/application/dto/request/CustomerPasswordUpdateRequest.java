package woowacourse.shoppingcart.customer.application.dto.request;

import javax.validation.constraints.NotNull;

public class CustomerPasswordUpdateRequest {

    @NotNull
    private String password;
    @NotNull
    private String newPassword;

    public CustomerPasswordUpdateRequest() {
    }

    public CustomerPasswordUpdateRequest(final String password, final String newPassword) {
        this.password = password;
        this.newPassword = newPassword;
    }

    public String getPassword() {
        return password;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
