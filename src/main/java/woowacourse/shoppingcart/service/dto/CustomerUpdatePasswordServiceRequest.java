package woowacourse.shoppingcart.service.dto;

public class CustomerUpdatePasswordServiceRequest {
    private String oldPassword;
    private String newPassword;

    public CustomerUpdatePasswordServiceRequest() {
    }

    public CustomerUpdatePasswordServiceRequest(String oldPassword, String newPassword) {
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
