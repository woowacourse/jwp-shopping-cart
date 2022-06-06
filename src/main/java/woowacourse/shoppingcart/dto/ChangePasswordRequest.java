package woowacourse.shoppingcart.dto;

public class ChangePasswordRequest {

    private final String prevPassword;
    private final String newPassword;

    public ChangePasswordRequest(String prevPassword, String newPassword) {
        this.prevPassword = prevPassword;
        this.newPassword = newPassword;
    }

    public String getPrevPassword() {
        return prevPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
