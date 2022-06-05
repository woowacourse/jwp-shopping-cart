package woowacourse.shoppingcart.dto;

import woowacourse.auth.support.PasswordCheck;

public class UpdatePasswordRequest {

    @PasswordCheck
    private String password;
    @PasswordCheck
    private String newPassword;

    private UpdatePasswordRequest() {
    }

    public UpdatePasswordRequest(String password, String newPassword) {
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
