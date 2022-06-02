package woowacourse.shoppingcart.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import woowacourse.util.PasswordCheck;

public class ChangePasswordRequest {
    private static final String INVALID_PASSWORD = "[ERROR] 비밀번호는 공백 또는 빈 값일 수 없습니다.";
    private static final String INVALID_LENGTH_PASSWORD = "[ERROR] 비밀번호는 6자 이상 이어야 합니다.";
    @NotBlank(message = INVALID_PASSWORD)
    @Size(min = 6, message = INVALID_LENGTH_PASSWORD)
    @PasswordCheck
    private final String oldPassword;
    @NotBlank(message = INVALID_PASSWORD)
    @Size(min = 6, message = INVALID_LENGTH_PASSWORD)
    @PasswordCheck
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
