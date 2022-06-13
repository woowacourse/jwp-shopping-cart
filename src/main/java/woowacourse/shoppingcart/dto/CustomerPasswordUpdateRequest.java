package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.PlainPassword;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CustomerPasswordUpdateRequest {

    private static final String INVALID_PASSWORD_LENGTH_MESSAGE = "비밀번호는 8자 이상이어야 합니다.";

    @NotBlank(message = INVALID_PASSWORD_LENGTH_MESSAGE)
    @Size(min = PlainPassword.MIN_RAW_VALUE_LENGTH, message = INVALID_PASSWORD_LENGTH_MESSAGE)
    private String oldPassword;

    @NotBlank(message = INVALID_PASSWORD_LENGTH_MESSAGE)
    @Size(min = PlainPassword.MIN_RAW_VALUE_LENGTH, message = INVALID_PASSWORD_LENGTH_MESSAGE)
    private String newPassword;

    private CustomerPasswordUpdateRequest() {
    }

    public CustomerPasswordUpdateRequest(final String oldPassword, final String newPassword) {
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
