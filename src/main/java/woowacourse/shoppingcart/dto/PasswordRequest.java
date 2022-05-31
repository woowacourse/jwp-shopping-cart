package woowacourse.shoppingcart.dto;

public class PasswordRequest {

    private String oldPassword;
    private String newPassword;

    public PasswordRequest() {
    }

    public PasswordRequest(final String oldPassword, final String newPassword) {
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
