package woowacourse.shoppingcart.dto;

public class PasswordChangeRequest {

    private String oldPassword;
    private String newPassword;

    public PasswordChangeRequest() {
    }

    public PasswordChangeRequest(final String oldPassword, final String newPassword) {
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
