package woowacourse.shoppingcart.dto;

public class ChangePasswordRequest {

    private final String password;

    private final String newPassword;

    public ChangePasswordRequest(String password, String newPassword) {
        this.password = password;
        this.newPassword = newPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getPassword() {
        return password;
    }
}
