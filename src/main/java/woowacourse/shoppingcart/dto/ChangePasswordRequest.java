package woowacourse.shoppingcart.dto;

public class ChangePasswordRequest {

    private final String oldPassword;

    private final String newPassword;

    public ChangePasswordRequest(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }
}
