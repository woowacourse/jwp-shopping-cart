package woowacourse.shoppingcart.dto;

import org.hibernate.validator.constraints.Length;
import woowacourse.auth.support.PasswordCheck;

public class UpdatePasswordRequest {

    @PasswordCheck
    @Length(min = 6)
    private String password;

    @PasswordCheck
    @Length(min = 6)
    private String newPassword;

    public UpdatePasswordRequest() {
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
