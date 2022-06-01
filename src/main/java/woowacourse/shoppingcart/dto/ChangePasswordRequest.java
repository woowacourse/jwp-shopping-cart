package woowacourse.shoppingcart.dto;

import javax.validation.constraints.NotBlank;

public class ChangePasswordRequest {
    @NotBlank(message = "[ERROR] 비밀번호는 공백 또는 빈 값일 수 없습니다.")
    private final String oldPassword;
    @NotBlank(message = "[ERROR] 비밀번호는 공백 또는 빈 값일 수 없습니다.")
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
