package woowacourse.shoppingcart.application.dto.request;

public class CustomerUpdatePasswordRequest {

    private String oldPassword;
    private String newPassword;

    private CustomerUpdatePasswordRequest() {
    }

    public CustomerUpdatePasswordRequest(final String oldPassword, final String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
