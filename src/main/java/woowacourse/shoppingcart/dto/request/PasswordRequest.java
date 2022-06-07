package woowacourse.shoppingcart.dto.request;

public class PasswordRequest {
    private String oldPassword;
    private String newPassword;

    private PasswordRequest() {
    }

    public PasswordRequest(String oldPassword, String newPassword) {
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
